package com.johneycodes.store;


import com.johneycodes.store.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) {
//		ApplicationContext = SpringApplication.run(StoreApplication.class, args);
		var user = User.builder()
				.name("John Doe")
				.email("email")
				.password("password")
				.build();
		System.out.println(user);

	}

}
