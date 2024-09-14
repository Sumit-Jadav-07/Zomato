package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entity.CustomerEntity;
import com.entity.RestaurantEntity;
import com.repository.CustomerRepository;
import com.repository.RestaurantRepository;
import com.service.LoginRequest;
import com.service.OtpService;
import com.service.Services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
  OtpService otpservice;

  @Autowired
  JavaMailSender sender;

  @Autowired
  Services service;

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
          return service.customerLogin(loginRequest);
        case "restaurant":
          return service.restaurantLogin(loginRequest);
        default:
          return ResponseEntity.badRequest().body("Invalid Role Specified");
      }
    }

  }

  @GetMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

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

  @PostMapping("/sendotp")
  public ResponseEntity<String> sendOtp(@RequestParam String email, @RequestParam String role, HttpSession session) {
    Object entity = service.findEntityByEmailAndRole(email, role);
    if (entity == null) {
      return ResponseEntity.badRequest().body(role + " email not found");
    }
    String otp = otpservice.getOtp();
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(email);
    message.setSubject("OTP");
    message.setText(otp);
    sender.send(message);
    session.setAttribute("otp", otp);
    return ResponseEntity.ok("OTP sent successfully");
  }

  @PostMapping("/forgotpassword")
  public ResponseEntity<String> forgotPassword(@RequestParam String email, @RequestParam String password,
      @RequestParam String role) {
    Object entity = service.findEntityByEmailAndRole(email, role);
    if (entity == null) {
      return ResponseEntity.badRequest().body(role + " email not found");
    }
    if (entity instanceof CustomerEntity) {
      ((CustomerEntity) entity).setPassword(password);
      customerRepo.save((CustomerEntity) entity);
    } else if (entity instanceof RestaurantEntity) {
      ((RestaurantEntity) entity).setPassword(password);
      restaurantRepo.save((RestaurantEntity) entity);
    }
    return ResponseEntity.ok("Password updated successfully");
  }

}
