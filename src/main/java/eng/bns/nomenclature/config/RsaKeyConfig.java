package eng.bns.nomenclature.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class RsaKeyConfig {

    @Bean
    public RSAPublicKey publicKey() throws NoSuchAlgorithmException {
        return (RSAPublicKey) keyPair().getPublic();
    }
    @Bean
    public RSAPrivateKey privateKey() throws NoSuchAlgorithmException {
        return (RSAPrivateKey) keyPair().getPrivate();
    }


    @Bean
    protected KeyPair keyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator   = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
}
