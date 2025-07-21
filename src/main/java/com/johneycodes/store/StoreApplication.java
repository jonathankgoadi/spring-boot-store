package com.johneycodes.store;



import com.johneycodes.store.entities.Tag;
import com.johneycodes.store.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) {
//		ApplicationContext = SpringApplication.run(StoreApplication.class, args);
		var user = User.builder()
				.name("jonathan")
				.email("jonathankgoadi@gmail.com")
				.password("password")
				.build();


		user.addTag("tag1");
		System.out.println(user);

	}

}
