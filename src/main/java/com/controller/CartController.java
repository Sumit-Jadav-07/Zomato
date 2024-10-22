package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entity.CartEntity;
import com.entity.CustomerEntity;
import com.entity.RestaurantEntity;
import com.repository.CartRepository;
import com.repository.CustomerRepository;
import com.repository.MenuItemRepository;
import com.repository.RestaurantRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/private/cart")
public class CartController {

  @Autowired
  CartRepository cartRepo;

  @Autowired
  MenuItemRepository menuItemRepo;

  @Autowired
  CustomerRepository customerRepo;

  @Autowired
  RestaurantRepository restaurantRepo;

  @Autowired
  HttpServletRequest request;

  @PostMapping("/create/{restaurantId}")
  public ResponseEntity<?> creatCart(@PathVariable Integer restaurantId) {

    if (restaurantId == null) {
      return ResponseEntity.badRequest().body("ID cannot be null");
    }

    Integer customerId = (Integer) request.getAttribute("customerId");

    System.out.println("Customer Id : " + customerId);
    if (customerId == null) {
      return ResponseEntity.badRequest().body("CustomerId cannot be null");
    }

    Optional<CustomerEntity> customerOp = customerRepo.findById(customerId);
    if (customerOp.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
    }

    Optional<RestaurantEntity> restaurantOp = restaurantRepo.findById(restaurantId);
    if (restaurantOp.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
    }

    // Check if a cart already exists for this customer and restuarant
    Optional<CartEntity> cartOp = cartRepo.findByCustomerAndRestaurant(customerOp.get(), restaurantOp.get());
    if (cartOp.isPresent()) {
      return ResponseEntity.ok("Cart already exists");
    }

    // Create a new cart
    CartEntity cart = new CartEntity();
    cart.setCustomer(customerOp.get());
    cart.setRestaurant(restaurantOp.get());
    cartRepo.save(cart);

    return ResponseEntity.ok("Cart created successfully");
  }

  @GetMapping("/getCart/{cartId}")
  public ResponseEntity<?> getCart(@PathVariable Integer cartId) {
    Optional<CartEntity> cartOp = cartRepo.findById(cartId);
    if (cartOp.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
    }

    return ResponseEntity.ok(cartOp.get());
  }

  @GetMapping("/getAllCart")
  public ResponseEntity<?> getAllCart() {
    List<CartEntity> carts = cartRepo.findAll();
    if (carts.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carts not found");
    }
    return ResponseEntity.ok(carts);
  }

  @PutMapping("/update/{cartId}")
  public ResponseEntity<?> updateCart(@PathVariable Integer cartId,
      @RequestParam(required = false) Double offerAmount) {
    Optional<CartEntity> cartOp = cartRepo.findById(cartId);
    if (cartOp.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
    }

    CartEntity cart = cartOp.get();
    if (offerAmount != null) {
      cart.setOfferAmount(offerAmount);
    }

    cartRepo.save(cart);
    return ResponseEntity.ok("Cart updated successfully");
  }

  @DeleteMapping("/delete/{cartId}")
  public ResponseEntity<?> deleteCart(@PathVariable Integer cartId) {
    Optional<CartEntity> cartOp = cartRepo.findById(cartId);
    if (cartOp.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
    }

    cartRepo.delete(cartOp.get());
    return ResponseEntity.ok("Cart deleted successfully");
  }

}
