package com.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    // In-memory token store
    private Map<String, Object> tokenStore = new HashMap<>();

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

    public String generateToken(String email){
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, email);
        return token;
    }

    public String validateToken(String token){
        return tokenStore.get(token).toString();
    }
}
