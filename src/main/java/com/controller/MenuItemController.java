package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.MenuItemEntity;
import com.repository.MenuItemRepository;

@RestController
@RequestMapping("api/menuitem")
public class MenuItemController {

  @Autowired
  MenuItemRepository repo;

  @PostMapping
  public String addMenuItem(@RequestBody MenuItemEntity entity) {
    repo.save(entity);
    return "Success";
  }

  @GetMapping
  public List<MenuItemEntity> getMenuItem() {
    return repo.findAll();
  }

  @GetMapping("{itemId}")
  public ResponseEntity<MenuItemEntity> getMenuItem(@PathVariable Integer itemId) {
    Optional<MenuItemEntity> op = repo.findById(itemId);
    if (op.isPresent()) {
      return ResponseEntity.ok(op.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("{itemId}")
  public String deleteMenuItem(@PathVariable Integer itemId) {
    repo.deleteById(itemId);
    return "Success";
  }

  @PutMapping
  public ResponseEntity<?> updateItem(@RequestBody MenuItemEntity entity) {
    Optional<MenuItemEntity> op = repo.findById(entity.getItemId());
    if (op.isPresent()) {
      repo.save(entity);
      return ResponseEntity.ok(entity);
    } else {
      return ResponseEntity.ok("Invalid ItemId");
    }
  }

}
