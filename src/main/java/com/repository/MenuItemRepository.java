package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.entity.MenuItemEntity;

public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Integer> {
  
}
