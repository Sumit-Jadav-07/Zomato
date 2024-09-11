package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.CustomerAddressEntity;
@Repository
public interface CustomerAddressRespository extends JpaRepository<CustomerAddressEntity, Integer> {
  
}
