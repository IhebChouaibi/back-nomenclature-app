package eng.bns.nomenclature.security;

import eng.bns.nomenclature.entities.User;
import eng.bns.nomenclature.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
@Service




@RequiredArgsConstructor
public class UsersDetailsServiceImpl implements UserDetailsService {
private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUsersByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
       if (user == null){
           System.out.println("user not found ");
           throw new UsernameNotFoundException("User not found");
       }

        return new UserDetailsImpl(user);
}}
