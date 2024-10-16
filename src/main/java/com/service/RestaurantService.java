package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.entity.RestaurantEntity;
import com.repository.RestaurantRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class RestaurantService {

  @Autowired
  HttpServletResponse response;

  @Autowired
  BCryptPasswordEncoder encoder;

  @Autowired
  RestaurantRepository restaurantRepo;

  public ResponseEntity<?> restaurantLogin(LoginRequest loginRequest) {
    RestaurantEntity restaurant = restaurantRepo.findByEmail(loginRequest.getEmail());
    if (restaurant != null) {
      String encryptedPassword = restaurant.getPassword();
      if (encoder.matches(loginRequest.getPassword(), encryptedPassword)) {
        Cookie cookie = new Cookie("restaurant", String.valueOf(restaurant.getRestaurantId()));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(15 * 24 * 60 * 60);
        response.addCookie(cookie);
        return ResponseEntity.ok("Login Successful");
      } else {
        return ResponseEntity.badRequest().body("Invalid Password");
      }
    }
    return ResponseEntity.ok("Invalid Email");
  }

  public RestaurantEntity authenticateRestaurant(String email, String password){
    RestaurantEntity restaurant = restaurantRepo.findByEmail(email);
    if(restaurant != null && encoder.matches(password, restaurant.getPassword())){
      return restaurant;
    }
    return null;
  }

  public void saveToken(String email, String token){
    RestaurantEntity restaurant = restaurantRepo.findByEmail(email);
    restaurant.setResToken(token);
    restaurantRepo.save(restaurant);
  }

  public String getEmailByToken(String token){
    RestaurantEntity restaurant = restaurantRepo.findByResToken(token);
    return (restaurant != null) ? restaurant.getEmail() : null;
  }

  public boolean removeToken(String token){
    RestaurantEntity restaurant = restaurantRepo.findByResToken(token);
    if(restaurant != null){
      restaurant.setResToken(null);
      restaurantRepo.save(restaurant);
      return true;
    }
    return false;
  }

}
