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
@Table(name = "Cart")
public class CartEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer cartId;

  @ManyToOne
  @JoinColumn(name = "customerId")
  private CustomerEntity customer;

  @ManyToOne
  @JoinColumn(name = "restaurantId")
  private RestaurantEntity restaurant;

  Integer offerAmount;

}
