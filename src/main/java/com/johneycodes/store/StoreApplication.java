package com.johneycodes.store;



import com.johneycodes.store.entities.User;
import com.johneycodes.store.repositories.UserRepository;
import com.johneycodes.store.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(StoreApplication.class, args);

		var service = context.getBean(UserService.class);
		service.loyaltyPoint(2);







	}

}
