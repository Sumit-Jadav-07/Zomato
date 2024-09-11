package com.controller;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.MenuEntity;
import com.repository.MenuRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

  @Autowired
  MenuRepository repo;

  @PostMapping
  public String addMenu(@RequestBody MenuEntity entity) {
    repo.save(entity);
    return "Success";
  }

  @GetMapping
  public List<MenuEntity> listMenus() {
     List<MenuEntity> menus = repo.findAll();
     return menus;
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
