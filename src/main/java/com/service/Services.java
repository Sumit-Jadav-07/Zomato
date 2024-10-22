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
    private CustomerRepository customerRepo;

    @Autowired
    private RestaurantRepository restaurantRepo;

    // In-memory token store (Map of token to email and role)
    private Map<String, Map<String, String>> tokenStore = new HashMap<>();

    // Find entity (Customer or Restaurant) based on email and role
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

    // Generate a new token (UUID)
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    // Store token with associated email and role
    public void storeToken(String email, String role, String token) {
        Map<String, String> tokenDetails = new HashMap<>();
        tokenDetails.put("email", email);
        tokenDetails.put("role", role);
        tokenStore.put(token, tokenDetails);  // Map token to email and role
    }

    // Validate if the token exists in the store
    public boolean validateToken(String token) {
        return tokenStore.containsKey(token);
    }

    // Get email by token
    public String getEmailByToken(String token) {
        if (tokenStore.containsKey(token)) {
            return tokenStore.get(token).get("email");
        }
        return null;
    }

    // Get role by token
    public String getRoleByToken(String token) {
        if (tokenStore.containsKey(token)) {
            return tokenStore.get(token).get("role");
        }
        return null;
    }
}
