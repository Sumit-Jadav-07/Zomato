package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.CartEntity;
import com.entity.CustomerEntity;
import com.entity.RestaurantEntity;

public interface CartRepository extends JpaRepository<CartEntity, Integer> {

  Optional<CartEntity> findByCustomerAndRestaurant(CustomerEntity customerEntity, RestaurantEntity restaurantEntity);

}
