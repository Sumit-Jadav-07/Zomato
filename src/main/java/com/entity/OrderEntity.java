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
@Table(name = "Orders")
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer orderId;

  @ManyToOne
  @JoinColumn(name = "customerId")
  private CustomerEntity customer;

  @ManyToOne
  @JoinColumn(name = "cartId")
  private CartEntity cart;

  Double totalPaid;
  String authCode;
  Integer status;
  String paymentType;
  String orderDate;
  
}
