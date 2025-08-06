package com.johneycodes.store.services;

import com.johneycodes.store.repositories.UserRepository;
import java.util.Collections;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

       return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
