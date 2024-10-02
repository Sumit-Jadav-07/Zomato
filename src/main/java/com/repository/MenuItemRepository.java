package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.MenuEntity;
import com.entity.MenuItemEntity;

public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Integer> {

  List<MenuItemEntity> findByActiveStatus(Boolean activeStatus);
  List<MenuItemEntity> findByMenu(MenuEntity menu);
  List<MenuItemEntity> findByMenuMenuId(Integer menuId);
  
}
