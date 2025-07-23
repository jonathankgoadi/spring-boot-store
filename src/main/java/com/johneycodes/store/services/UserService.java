package com.johneycodes.store.services;

import com.johneycodes.store.repositories.ProfileRepository;
import com.johneycodes.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Transactional
    public void loyaltyPoint(int points) {
       var users = userRepository.findLoyalUsers(points);
       users.forEach(user -> {
           System.out.println(user.getId() + " " + user.getEmail());
       });


    }


}
