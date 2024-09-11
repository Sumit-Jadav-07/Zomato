package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.RestaurantEntity;
import com.repository.RestaurantRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

  @Autowired
  RestaurantRepository repo;

  @PostMapping
  public String addRestaurant(@RequestBody RestaurantEntity entity) {
      repo.save(entity);
      return "Success";
  }

  @GetMapping
  public List<RestaurantEntity> listRestaurants() {
    List<RestaurantEntity> restaurants = repo.findAll();
    return restaurants;
  }

  @GetMapping("{restaurantId}")
  public ResponseEntity<RestaurantEntity> getRestaurant(@PathVariable("restaurantId") Integer restaurantId){
    Optional<RestaurantEntity> op = repo.findById(restaurantId);
    if (op.isPresent()) {
			return ResponseEntity.ok(op.get());
		} else {
			return ResponseEntity.notFound().build();
		}
  }

  @DeleteMapping("{restaurantId}")
  public String deleteRestaurant(@PathVariable("restaurantId") Integer restaurantId){
    repo.deleteById(restaurantId);
    return "Success";
  }

  @PutMapping
  public ResponseEntity<?> updateRestaurant(@RequestBody RestaurantEntity entity) {
      Optional<RestaurantEntity> op = repo.findById(entity.getRestaurantId());
      if(op.isEmpty()){
        return ResponseEntity.ok("Invalid RestaurantId");
      } else {
        repo.save(entity);
        return ResponseEntity.ok(entity);
      }
  }
  
  
}
