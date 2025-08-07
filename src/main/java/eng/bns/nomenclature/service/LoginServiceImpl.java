package eng.bns.nomenclature.service;

import com.nimbusds.jose.JOSEException;
import eng.bns.nomenclature.dto.AuthRequest;
import eng.bns.nomenclature.dto.AuthResponse;
import eng.bns.nomenclature.dto.SectionDto;
import eng.bns.nomenclature.entities.Role;
import eng.bns.nomenclature.entities.Section;
import eng.bns.nomenclature.entities.User;
import eng.bns.nomenclature.repository.RoleRepository;
import eng.bns.nomenclature.repository.SectionRepository;
import eng.bns.nomenclature.repository.UserRepository;
import eng.bns.nomenclature.security.JwtService;
import eng.bns.nomenclature.security.UserDetailsImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final JwtService    jwtService;
private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public LoginServiceImpl(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }


    @Override
    public AuthResponse login(AuthRequest authRequest) throws JOSEException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword())
        );
        User user = userRepository.findUsersByUsername(authRequest.getUsername()).orElseThrow(()->new UsernameNotFoundException("User not found"));
    Role role = roleRepository.findByUsersId(user.getId());
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
String  token = jwtService.generateToken(userDetails);
return new AuthResponse(token ,user.getEmail(),role.getName());




    }




}

