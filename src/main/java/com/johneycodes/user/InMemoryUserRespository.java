package com.johneycodes.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class InMemoryUserRespository implements UserRepository {
    private final HashMap<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        System.out.println("Saving user: " + user);
        users.put(user.getEmail(),user);
    }
}
