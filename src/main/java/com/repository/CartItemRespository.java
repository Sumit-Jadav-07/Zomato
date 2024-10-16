package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.CartEntity;
import com.entity.CartItemEntity;
import com.entity.MenuItemEntity;

public interface CartItemRespository extends JpaRepository<CartItemEntity, Integer> {

  Optional<CartItemEntity> findByCartAndItem(CartEntity cart, MenuItemEntity menuItemEntity);

  List<CartItemEntity> findByCart(CartEntity cart);
  
}
