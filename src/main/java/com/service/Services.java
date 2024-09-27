package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.entity.CustomerEntity;
import com.entity.RestaurantEntity;
import com.repository.CustomerRepository;
import com.repository.RestaurantRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class Services {

    @Autowired
    HttpServletResponse response;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    RestaurantRepository restaurantRepo;

    public ResponseEntity<?> customerLogin(LoginRequest loginRequest) {
        CustomerEntity customer = customerRepo.findByEmail(loginRequest.getEmail());
        if (customer != null) {
            String encryptedPassword = customer.getPassword();
            if (encoder.matches(loginRequest.getPassword(), encryptedPassword)) {
                Cookie cookie = new Cookie("customer", String.valueOf(customer.getCustomerId()));
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setMaxAge(15 * 24 * 60 * 60);
                response.addCookie(cookie);
                List<RestaurantEntity> restaurants = restaurantRepo.findAll();
                LoginResponse login = new LoginResponse("Login Successful", restaurants);
                return ResponseEntity.ok(login);
            } else {
                return ResponseEntity.badRequest().body("Invalid Password");
            }
        }
        return ResponseEntity.ok("Invalid Email");
    }

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

    public Object findEntityByEmailAndRole(String email, String role) {
        switch (role.toLowerCase()) {
            case "customer":
                return customerRepo.findByEmail(email);
            case "restaurant":
                return restaurantRepo.findByEmail(email);
            default:
                return null;
        }
    }

}
