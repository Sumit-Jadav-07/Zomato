package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.RestaurantEntity;
import java.util.List;


@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {
  RestaurantEntity findByEmail(String email);
  List<RestaurantEntity> findByActiveStatus(Boolean activeStatus);
  RestaurantEntity findByResToken(String token);
}
