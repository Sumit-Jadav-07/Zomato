package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.CustomerAddressEntity;
import com.repository.CustomerAddressRespository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/private/customeraddress")
public class CustomerAddressController {

  @Autowired
  CustomerAddressRespository repo;

  @PostMapping
  public String addAddress(@RequestBody CustomerAddressEntity entity) {
      repo.save(entity);
      return "Success";
  }

  @GetMapping
  public List<CustomerAddressEntity> listCustomerAddresses() {
    List<CustomerAddressEntity> addresses = repo.findAll();
      return addresses;
  }

  @GetMapping("{addressId}")
  public ResponseEntity<CustomerAddressEntity> getAddress(@PathVariable Integer addressId) {
    Optional<CustomerAddressEntity> op = repo.findById(addressId);
    if(op.isPresent()) {
      return ResponseEntity.ok(op.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("{addressId}")
  public String deleteAddress(@PathVariable Integer addressId){
    repo.deleteById(addressId);
    return "Success";
  }

  @PutMapping
  public ResponseEntity<?> updateAddress(@RequestBody CustomerAddressEntity entity) {
      Optional<CustomerAddressEntity> op = repo.findById(entity.getAddressId());
      if (op.isEmpty()) {
        return ResponseEntity.ok("Invalid AddressId");
      } else {
        repo.save(entity);
        return ResponseEntity.ok(entity);
      }
  }
  
  
  
  
}
