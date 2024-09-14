package com.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class RestaurantAddressValidators {

    public Map<String, String> restaurantAddressValidation(String address, String street, String landmark, String city, String state, String pincode) {
        Map<String, String> errors = new HashMap<>();

        if (address == null || address.trim().isEmpty()) {
            errors.put("addressError", "Please enter address");
        }
        if (street == null || street.trim().isEmpty()) {
            errors.put("streetError", "Please enter street");
        }
        if (landmark == null || landmark.trim().isEmpty()) {
            errors.put("landmarkError", "Please enter landmark");
        }
        if (city == null || city.trim().isEmpty()) {
            errors.put("cityError", "Please enter city");
        }
        if (state == null || state.trim().isEmpty()) {
            errors.put("stateError", "Please enter state");
        }
        if (pincode == null || pincode.trim().isEmpty()) {
            errors.put("pincodeError", "Please enter pincode");
        } else if (!pincode.matches("\\d{6}")) {
            errors.put("pincodeError", "Pincode must be 6 digits long");
        }

        return errors;
    }
}
