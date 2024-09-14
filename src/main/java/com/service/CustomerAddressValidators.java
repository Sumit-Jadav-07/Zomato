package com.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CustomerAddressValidators {

    public Map<String, String> customerAddressValidation(String title, String houseNo, String apartmentName,
                                                         String street, String landmark,
                                                         String city, String state, String pincode) {
        Map<String, String> errors = new HashMap<>();

        // Validate title
        if (title == null || title.trim().isEmpty()) {
            errors.put("titleError", "Please enter title");
        }

        // Validate houseNo
        if (houseNo == null || houseNo.trim().isEmpty()) {
            errors.put("houseNoError", "Please enter house number");
        }

        // Validate apartmentName
        if (apartmentName == null || apartmentName.trim().isEmpty()) {
            errors.put("apartmentNameError", "Please enter apartment name");
        }

        // Validate street
        if (street == null || street.trim().isEmpty()) {
            errors.put("streetError", "Please enter street");
        }

        // Validate landmark
        if (landmark == null || landmark.trim().isEmpty()) {
            errors.put("landmarkError", "Please enter landmark");
        }

        // Validate city
        if (city == null || city.trim().isEmpty()) {
            errors.put("cityError", "Please enter city");
        }

        // Validate state
        if (state == null || state.trim().isEmpty()) {
            errors.put("stateError", "Please enter state");
        }

        // Validate pincode
        if (pincode == null || pincode.trim().isEmpty()) {
            errors.put("pincodeError", "Please enter pincode");
        } else if (!pincode.matches("\\d{6}")) {
            errors.put("pincodeError", "Pincode must be 6 digits long");
        }

        return errors;
    }

}
