package com.johneycodes.store.repositories;

import com.johneycodes.store.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
