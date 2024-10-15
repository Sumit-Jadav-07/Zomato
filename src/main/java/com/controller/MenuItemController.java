package com.controller;

import java.util.HashMap;
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

import com.entity.MenuEntity;
import com.entity.MenuItemEntity;
import com.repository.MenuItemRepository;
import com.repository.MenuRepository;

@RestController
@RequestMapping("api/private/menuitem")
public class MenuItemController {

  @Autowired
  MenuItemRepository repo;

  @Autowired
  MenuRepository menuRepo;

  @PostMapping("{menuId}")
  public String addMenuItem(@RequestBody MenuItemEntity entity, @PathVariable Integer menuId) {
    if(entity.getMenu() == null){
      MenuEntity menu = new MenuEntity();
      menu.setMenuId(menuId);
      entity.setMenu(menu);
    }
    repo.save(entity);
    return "Success";
  }

  @GetMapping
  public List<MenuItemEntity> getAllMenuItem() {
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

  @GetMapping("/menu/{menuId}")
  public ResponseEntity<?> getMenuItemsByMenuId(@PathVariable Integer menuId) {
    List<MenuItemEntity> menuItems = repo.findByMenuMenuId(menuId);
    MenuEntity menu = menuRepo.findById(menuId).get();
    HashMap<String, Object> obj = new HashMap<String, Object>();
    obj.put("menuitems",menuItems);
    obj.put("menu",menu);
    if (menuItems.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(obj);
  }

  @DeleteMapping("{itemId}")
  public String deleteMenuItem(@PathVariable Integer itemId) {
    repo.deleteById(itemId);
    return "Success";
  }

  @PutMapping
  public ResponseEntity<?> updateMenuItem(@RequestBody MenuItemEntity entity) {
    Optional<MenuItemEntity> op = repo.findById(entity.getItemId());
    if (op.isPresent()) {
      repo.save(entity);
      return ResponseEntity.ok(entity);
    } else {
      return ResponseEntity.ok("Invalid ItemId");
    }
  }

}
