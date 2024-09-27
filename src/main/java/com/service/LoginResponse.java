package com.service;

import java.util.List;

import com.entity.RestaurantEntity;

import lombok.Data;

@Data
public class LoginResponse {
  private String message;
  private List<RestaurantEntity> restaurants;

  public LoginResponse(String message, List<RestaurantEntity> restaurants) {
    this.message = message;
    this.restaurants = restaurants;
  }

}
