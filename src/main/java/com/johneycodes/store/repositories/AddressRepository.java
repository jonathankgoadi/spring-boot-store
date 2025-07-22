package com.johneycodes.store.repositories;

import com.johneycodes.store.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}