package com.controller;

import com.entity.OrderEntity;
import com.repository.OrderRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/api/private/order")
public class OrderController {

  @Autowired
  private OrderRepository orderRepo;

  @GetMapping("/getAllOrders")
  public List<OrderEntity> getAllOrder() {
      return orderRepo.findAll();
  }
  
  
}
