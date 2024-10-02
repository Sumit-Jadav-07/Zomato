package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "CartItem")
public class CartItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer cartItemId;

  @ManyToOne
  @JoinColumn(name = "menuId")
  private MenuEntity menu;

  @ManyToOne
  @JoinColumn(name = "itemId")
  private MenuItemEntity item;

  Integer quantity;

  @ManyToOne
  @JoinColumn(name = "cartId")
  private CartEntity cart;

}
