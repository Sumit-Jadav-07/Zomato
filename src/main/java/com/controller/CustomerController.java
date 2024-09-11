package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.CustomerEntity;
import com.repository.CustomerRepository;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	CustomerRepository repo;

	@PostMapping
	public String addCustomer(@RequestBody CustomerEntity entity) {
		System.out.println(entity.getFullName());
		System.out.println(entity.getEmail());
		repo.save(entity);
		return "Success";
	}

	@GetMapping
	public List<CustomerEntity> listCustomers() {
		List<CustomerEntity> customers = repo.findAll();
		return customers;
	}

	@GetMapping("{customerId}")
	public ResponseEntity<CustomerEntity> getCustomer(@PathVariable("customerId") Integer customerId) {
		Optional<CustomerEntity> op = repo.findById(customerId);
		if (op.isPresent()) {
			return ResponseEntity.ok(op.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("{customerId}")
	public String deleteCustomer(@PathVariable("customerId") Integer customerId) {
		repo.deleteById(customerId);
		return "success";
	}

	@PutMapping
	public ResponseEntity<?> updateCustomer(@RequestBody CustomerEntity entity) {
		Optional<CustomerEntity> op = repo.findById(entity.getCustomerId());
		if (op.isEmpty()) {
			return ResponseEntity.ok("Invalid CustomerId");
		} else {
			repo.save(entity);
			return ResponseEntity.ok(entity);
		}
	}

}
