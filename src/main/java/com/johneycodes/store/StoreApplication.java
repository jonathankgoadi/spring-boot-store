package com.johneycodes.store;



import com.johneycodes.store.entities.Profile;
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

		var profile = Profile.builder()
						.bio("biosss")
						.build();

		user.setProfile(profile);
		profile.setUser(user);

		System.out.println(user);

	}

}
