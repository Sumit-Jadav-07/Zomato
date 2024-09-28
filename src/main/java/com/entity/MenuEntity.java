package com.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Menu")
public class MenuEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer menuId;

  @ManyToOne
  @JoinColumn(name = "restaurantId")
  private RestaurantEntity restaurant;

  String menuTitle;
  String description;
  String menuImagePath;
  Boolean activeStatus = true;
  String menuPrice;

  @OneToMany(mappedBy = "menu")
  private List<MenuItemEntity> menuItems;

  @OneToMany(mappedBy = "menu")
  private List<CartEntity> carts;
  
}
