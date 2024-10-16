package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entity.CartEntity;
import com.entity.CartItemEntity;
import com.entity.MenuEntity;
import com.entity.MenuItemEntity;
import com.repository.CartItemRespository;
import com.repository.CartRepository;
import com.repository.MenuItemRepository;
import com.repository.MenuRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/private/cartItem")
public class CartItemController {

  @Autowired
  CartRepository cartRepo;

  @Autowired
  MenuRepository menuRepo;

  @Autowired
  MenuItemRepository menuItemRepo;

  @Autowired
  CartItemRespository cartItemRepo;

  @PostMapping("/add")
  public ResponseEntity<?> addItemToCart(@RequestParam Integer cartId, @RequestParam Integer itemId,
      @RequestParam Integer quantity) {
    Optional<CartEntity> cartOp = cartRepo.findById(cartId);
    if (cartOp.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cart not found");
    }

    Optional<MenuItemEntity> menuItemOp = menuItemRepo.findById(itemId);
    if (menuItemOp.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Menu item not found");
    }

    CartEntity cart = cartOp.get();
    Integer restaurantId = cart.getRestaurant().getRestaurantId();

    Optional<MenuEntity> menuOp = menuRepo.findFirstByRestaurantRestaurantId(restaurantId);
    if (menuOp.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu not found");
    }

    MenuEntity menuEntity = menuOp.get();

    Optional<CartItemEntity> existingCartItemOp = cartItemRepo.findByCartAndItem(cart, menuItemOp.get());
    CartItemEntity cartItem;

    if (existingCartItemOp.isPresent()) {
      cartItem = existingCartItemOp.get();
      cartItem.setQuantity(cartItem.getQuantity() + quantity);
    } else {
      cartItem = new CartItemEntity();
      cartItem.setCart(cart);
      cartItem.setItem(menuItemOp.get());
      cartItem.setMenu(menuEntity);
      cartItem.setQuantity(quantity);
    }

    cartItemRepo.save(cartItem);
    return ResponseEntity.ok("Item added to cart");
  }

  @DeleteMapping("/delete")
  public ResponseEntity<?> deleteCartItem(@RequestParam Integer cartItemId) {
    Optional<CartItemEntity> cartItemOp = cartItemRepo.findById(cartItemId);
    if (cartItemOp.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found");
    }
    cartItemRepo.delete(cartItemOp.get());
    return ResponseEntity.ok("Item removed from cart");
  }

  @PutMapping("/update")
  public ResponseEntity<?> updateCartItem(@RequestParam Integer cartItemId, @RequestParam Integer quantity) {
    Optional<CartItemEntity> cartItemOp = cartItemRepo.findById(cartItemId);
    if (cartItemOp.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found");
    }
    CartItemEntity cartItem = cartItemOp.get();
    cartItem.setQuantity(quantity);
    cartItemRepo.save(cartItem);
    return ResponseEntity.ok("Item quantity updated in cart");
  }

  @GetMapping("/items")
  public ResponseEntity<?> getCartItems(@RequestParam Integer cartId) {
    Optional<CartEntity> cartOp = cartRepo.findById(cartId);
    if (cartOp.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
    }
    CartEntity cart = cartOp.get();
    List<CartItemEntity> cartItems = cartItemRepo.findByCart(cart);
    return ResponseEntity.ok(cartItems);
  }

}
