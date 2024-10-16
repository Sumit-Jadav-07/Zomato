package com.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Customers")
public class CustomerEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer customerId;
	String fullName;
	String email;
	String password;
	String gender;
	String birthdate;
	String contactNumber;
	String address1;
	String address2;
	String customerImagePath;
	String cusToken;

	// @OneToMany(mappedBy = "customer")
	// private List<CustomerAddressEntity> CustomerAddresses;

	// @OneToMany(mappedBy = "customer")
	// private List<CartEntity> carts;

}
