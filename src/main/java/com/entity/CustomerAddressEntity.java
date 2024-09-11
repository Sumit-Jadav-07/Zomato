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
@Table(name = "CustomerAddress")
public class CustomerAddressEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer addressId;

  @ManyToOne
  @JoinColumn(name = "customerId")
  private CustomerEntity customer;

  String title;
  String houseNo;
  String apartmentName;
  String street;
  String landmark;
  String city;
  String state;
  String pincode;
  BigDecimal latitude;
  BigDecimal longitude;

}
