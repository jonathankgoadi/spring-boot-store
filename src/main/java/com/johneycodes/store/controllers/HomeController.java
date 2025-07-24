package com.johneycodes.store.controllers;

import com.johneycodes.store.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public User index() {
        var user = User.builder()
                .name("John Doe")
                .email("234 avenue")
                .password("1234")
                .build();
        return user;
    }


}
