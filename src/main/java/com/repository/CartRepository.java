package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Integer> {

}
