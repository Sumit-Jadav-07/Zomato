package com.repository;

import com.entity.MenuEntity;
import com.entity.RestaurantEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {

  List<MenuEntity> findByRestaurant(RestaurantEntity restaurant);
  List<MenuEntity> findByRestaurantRestaurantId(Integer restaurantId);
  Optional<MenuEntity> findFirstByRestaurantRestaurantId(Integer restaurantId);
}
