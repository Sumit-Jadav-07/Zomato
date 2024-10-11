package com.repository;

import com.entity.MenuEntity;
import com.entity.RestaurantEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {

  List<MenuEntity> findByRestaurant(RestaurantEntity restaurant);
  List<MenuEntity> findByRestaurantRestaurantId(Integer restaurantId);
}
