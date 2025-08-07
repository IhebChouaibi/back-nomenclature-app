package eng.bns.nomenclature.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import eng.bns.nomenclature.config.RsaKeyConfig;

import jakarta.annotation.PostConstruct;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
   private final RsaKeyConfig rsaKeyConfig ;

   private RSAPrivateKey privateKey ;
   private RSAPublicKey publicKey;

    public JwtService(RsaKeyConfig rsaKeyConfig) {
        this.rsaKeyConfig = rsaKeyConfig;
    }
@PostConstruct
public void initKeys() throws NoSuchAlgorithmException {
    try {
        this.privateKey = rsaKeyConfig.privateKey();
        this.publicKey = rsaKeyConfig.publicKey();
    } catch (Exception e) {
        System.out.println("Erreur d'initialisation des clés RSA"+"===="+ e.getMessage());
        throw new RuntimeException(e);
    }
}
    public String generateToken(UserDetails userDetails) throws JOSEException {
        List<String> authorities =userDetails.getAuthorities().stream().
            map(GrantedAuthority::getAuthority)
                .toList();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(userDetails.getUsername())
                .issuer("nomenclture-app")
                .expirationTime(Date.from(Instant.now().plusSeconds(86400)))
                .claim("auth",authorities)
                .build();
        JWSSigner signer = new RSASSASigner(privateKey);
        SignedJWT signedJWT = new SignedJWT(
               new JWSHeader.Builder(JWSAlgorithm.RS256).build(),claims);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }


    public boolean validToken(String token) {

        try{
         SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new RSASSAVerifier( publicKey);
            return signedJWT.verify(verifier) && !signedJWT.getJWTClaimsSet().getExpirationTime().before(new Date());

        }catch (Exception e){
            return false;
        }
    }
    public String excractUsername (String tken) throws ParseException{
        return SignedJWT.parse(tken).getJWTClaimsSet().getSubject();
    }
    public List<String> extractRoles (String tken) throws ParseException{
        return SignedJWT.parse(tken).getJWTClaimsSet().getStringListClaim("auth");
    }

}