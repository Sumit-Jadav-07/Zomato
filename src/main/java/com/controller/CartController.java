package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.repository.CartRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/cart")
public class CartController {

  @Autowired
  CartRepository cartRepo;

  @Autowired
  HttpServletResponse response;

  @Autowired
  HttpServletRequest request;

  @PostMapping
  public ResponseEntity<?> addToCart(@RequestParam Integer itemId, @RequestParam Integer quantity) {
    // Integer loginCustomerId = Integer.parseInt(getCookie("customer"));
    // if(loginCustomerId == null){
    //   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login as a customer");
    // }

    return ResponseEntity.ok("Success");
  }

  public String getCookie(String name) {
    String value = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name)) {
          value = cookie.getValue();
          break;
        }
      }
    }
    return value;
  }

}
