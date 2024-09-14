package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.CustomerEntity;
import com.entity.RestaurantEntity;
import com.repository.CustomerRepository;
import com.repository.RestaurantRepository;
import com.service.LoginRequest;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/session")
public class SessionController {

  @Autowired
  CustomerRepository customerRepo;

  @Autowired
  RestaurantRepository restaurantRepo;

  @Autowired
  BCryptPasswordEncoder encoder;

  @Autowired
  HttpServletResponse response;

  @PostMapping("/customer")
  public String addCustomer(@RequestBody CustomerEntity entity) {
    System.out.println(entity.getFullName());
    System.out.println(entity.getEmail());
    entity.setPassword(encoder.encode(entity.getPassword()));
    customerRepo.save(entity);
    return "Success";
  }

  @PostMapping("/restaurant")
  public String addRestaurant(@RequestBody RestaurantEntity entity) {
    restaurantRepo.save(entity);
    return "Success";
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    if (loginRequest.getRole() == null) {
      return ResponseEntity.badRequest().body("Role not specified");
    } else {
      switch (loginRequest.getRole().toLowerCase()) {
        case "customer":
          return customerLogin(loginRequest);
        case "restaurant":
          return restaurantLogin(loginRequest);
        default:
          return ResponseEntity.badRequest().body("Invalid Role Specified");
      }
    }

  }

  @GetMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request) {

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("customer") || cookie.getName().equals("restaurant")) {
          cookie.setValue("");
          cookie.setPath("/");
          cookie.setMaxAge(0);
          response.addCookie(cookie);
          return ResponseEntity.ok("Logout Successful");
        }
      }
    }
    return ResponseEntity.badRequest().body("No active session found");
  }

  private ResponseEntity<?> customerLogin(LoginRequest loginRequest) {
    CustomerEntity customer = customerRepo.findByEmail(loginRequest.getEmail());
    if (customer != null) {
      String encryptedPassword = customer.getPassword();
      if (encoder.matches(loginRequest.getPassword(), encryptedPassword)) {
        Cookie cookie = new Cookie("customer", String.valueOf(customer.getCustomerId()));
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

  private ResponseEntity<?> restaurantLogin(LoginRequest loginRequest) {
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
