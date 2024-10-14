package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.repository.CustomerRepository;
import com.repository.RestaurantRepository;

@Service
public class Services {

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    RestaurantRepository restaurantRepo;

    public Object findEntityByEmailAndRole(String email, String role) {
        switch (role.toLowerCase()) {
            case "customer":
                return customerRepo.findByEmail(email);
            case "restaurant":
                return restaurantRepo.findByEmail(email);
            default:
                return null;
        }
    }

}
