package com.august.run.Config.Security;

import java.util.Set;
import java.util.HashSet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.august.run.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@AllArgsConstructor
public class SecurityUserDetailService implements UserDetailsService {
    
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String id) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        com.august.run.Model.User user = userRepository.findById(id);

        if (user != null) {
            grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
            return new User(user.getId(), user.getPassword(), grantedAuthorities);
        } else { throw new UsernameNotFoundException("Can Not Find User : " + id); }
    }
}
