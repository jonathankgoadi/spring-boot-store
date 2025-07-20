package com.johneycodes.store;

import com.johneycodes.user.User;
import com.johneycodes.user.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(StoreApplication.class, args);
			var userService =  context.getBean(UserService.class);
		User user = new User(8000L,"johannes","johannes@gmail.com","password123");
		userService.registerUser(user);


	}

}
