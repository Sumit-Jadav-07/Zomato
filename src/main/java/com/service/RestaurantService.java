package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.entity.RestaurantEntity;
import com.repository.RestaurantRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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

}
