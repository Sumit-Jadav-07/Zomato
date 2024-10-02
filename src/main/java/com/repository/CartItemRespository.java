package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.CartItemEntity;

public interface CartItemRespository extends JpaRepository<CartItemEntity, Integer> {
  
}
