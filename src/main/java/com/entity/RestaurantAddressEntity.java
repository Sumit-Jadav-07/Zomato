package com.entity;

import java.math.BigDecimal;

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
@Table(name = "RestaurantAddress")
public class RestaurantAddressEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer addressId;

  @ManyToOne
  @JoinColumn(name = "restaurantId")
  private RestaurantEntity restaurant;

  String restaurantName;
  String address;
  String street;
  String landmark;
  String city;
  String state;
  String pincode;
  BigDecimal latitude;
  BigDecimal longitude;
  
}
