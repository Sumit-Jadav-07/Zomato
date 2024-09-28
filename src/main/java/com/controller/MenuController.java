package com.controller;

import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.MenuEntity;
import com.entity.RestaurantEntity;
import com.repository.MenuRepository;
import com.repository.RestaurantRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

  @Autowired
  MenuRepository repo;

  @Autowired
  RestaurantRepository resRepo;

  @Autowired
  HttpServletRequest request;

  @Autowired
  HttpServletResponse response;

  @PostMapping
  public String addMenu(@RequestBody MenuEntity entity) {
    repo.save(entity);
    return "Success";
  }

  @GetMapping
  public ResponseEntity<?> listMenus(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    Integer restaurantId = null;

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("restaurant")) {
          try {
            restaurantId = Integer.parseInt(cookie.getValue());
          } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid restaurant ID in cookie");
          }
          break;
        }
      }
    }

    if (restaurantId != null) {
      Optional<RestaurantEntity> restaurant = resRepo.findById(restaurantId);
      if (restaurant.isPresent()) {
        List<MenuEntity> menus = repo.findByRestaurant(restaurant.get());
        if (!menus.isEmpty()) {
          return ResponseEntity.ok(menus);
        } else {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No menus found for this restaurant");
        }
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
      }
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Restaurant ID not found in cookies");
    }
  }

  @GetMapping("{menuId}")
  public ResponseEntity<MenuEntity> getMenu(@PathVariable Integer menuId) {
    Optional<MenuEntity> op = repo.findById(menuId);
    if (op.isPresent()) {
      return ResponseEntity.ok(op.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("{menuId}")
  public String deleteMenu(@PathVariable Integer menuId) {
    repo.deleteById(menuId);
    return "Success";
  }

  @PutMapping
  public ResponseEntity<?> updateMenu(@RequestBody MenuEntity entity) {
    Optional<MenuEntity> op = repo.findById(entity.getMenuId());
    if (op.isPresent()) {
      repo.save(entity);
      return ResponseEntity.ok(entity);
    } else {
      return ResponseEntity.ok("Invalid MenuId");
    }
  }

}
