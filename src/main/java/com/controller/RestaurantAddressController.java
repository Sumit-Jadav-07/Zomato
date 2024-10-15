package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.entity.RestaurantAddressEntity;
import com.repository.RestaurantAddressRepository;

@RestController
@RequestMapping("/api/private/restaurantaddress")
public class RestaurantAddressController {

  @Autowired
  RestaurantAddressRepository repo;

  @PostMapping
  public String addAddress(@RequestBody RestaurantAddressEntity entity) {
      repo.save(entity);
      return "Success";
  }

  @GetMapping
  public List<RestaurantAddressEntity> listAddress(){
      List<RestaurantAddressEntity> addresses = repo.findAll();
      return addresses;
  }

  @GetMapping("{addressId}")
  public ResponseEntity<RestaurantAddressEntity> getAddress(@PathVariable Integer addressId) {
      Optional<RestaurantAddressEntity> op = repo.findById(addressId);
      if(op.isPresent()) {
        return ResponseEntity.ok(op.get());
      } else {
        return ResponseEntity.notFound().build();
      }
  }

  @DeleteMapping("{addressId}")
  public String deleteAddress(@PathVariable Integer addressId){
    repo.deleteById(addressId);
    return "Success";
  }

  @PutMapping
  public ResponseEntity<?> updateAddress(@RequestBody RestaurantAddressEntity entity) {
      Optional<RestaurantAddressEntity> op = repo.findById(entity.getAddressId());
      if (op.isEmpty()) {
        return ResponseEntity.ok("Invalid AddressId");
      } else {
        repo.save(entity);
        return ResponseEntity.ok(entity);
      }
  }
  
}
