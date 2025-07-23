package com.johneycodes.store.repositories;

import com.johneycodes.store.dtos.UserSummary;
import com.johneycodes.store.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select u.id as id, u.email as email from User u where u.profile.loyaltyPoints > :loyaltyPointsIsGreaterThan order by u.email")
    List<UserSummary> findLoyalUsers(@Param("loyaltyPointsIsGreaterThan") int loyaltyPointsIsGreaterThan);
}
