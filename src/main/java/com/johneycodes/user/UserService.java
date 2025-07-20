package com.johneycodes.user;

import com.johneycodes.store.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;
    NotificationService notificationService;

    public UserService(UserRepository userRepository, NotificationService notificationService) {
        System.out.println("parameterised constructor");
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }


    public void registerUser(User user) {
        userRepository.save(user);
        notificationService.send("registered", user.getEmail());
    }
}
