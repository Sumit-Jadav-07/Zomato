package com.service;

import java.time.LocalTime;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class RestaurantValidators {

    public String restaurantNameValidation(String restaurantName, Model model) {
        String restaurantNameError = "";
        if (restaurantName == null || restaurantName.trim().length() == 0) {
            restaurantNameError = "Please enter restaurant name";
        } else if (restaurantName.length() < 2 || restaurantName.length() > 50) {
            restaurantNameError = "Restaurant name should be between 2 and 50 characters long";
        }
        model.addAttribute("restaurantNameError", restaurantNameError);
        return restaurantNameError;
    }
    public String emailValidation(String rEmail, Model model) {
        String emailError = "";
        String emailRegEx = "[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]{2,5}";
        if (rEmail == null || rEmail.trim().length() == 0) {
            emailError = "Please enter email-id";
        } else if (!rEmail.matches(emailRegEx)) {
            emailError = "Please enter valid email-id";
        }
        model.addAttribute("emailError", emailError);
        return emailError;
    }

    public String passwordValidation(String rPass, Model model) {
        String passError = "";
        if (rPass == null || rPass.trim().length() == 0) {
            passError = "Please enter password";
        } else if (rPass.length() < 8 || rPass.length() > 16) {
            passError = "Password should be between 8 and 16 characters long";
        } else if (!rPass.matches(".*[0-9].*")) {
            passError = "Password must have at least one digit";
        } else if (!rPass.matches(".*[A-Z].*")) {
            passError = "Password must have at least one upper character";
        } else if (!rPass.matches(".*[a-z].*")) {
            passError = "Password must have at least one lower character";
        } else if (!rPass.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            passError = "Password must have at least one special character";
        }
        model.addAttribute("passError", passError);
        return passError;
    }

    public String cpassValidation(String rPass, String rConfirmPass, Model model) {

        String cpassError = "";
        if (rConfirmPass == null || rConfirmPass.trim().length() == 0) {
            cpassError = "Please enter confirm password";
        } else if (rConfirmPass.equals(rPass) == false) {
            cpassError = "Confirm password must be match with password";
        }
        model.addAttribute("cpassError", cpassError);
        return cpassError;
    }

    public String categoryValidation(String category, Model model) {
        String categoryError = "";
        if (category == null || category.trim().length() == 0) {
            categoryError = "Please enter category";
        } else if (category.length() < 2 || category.length() > 50) {
            categoryError = "Category should be between 2 and 50 characters long";
        }
        model.addAttribute("categoryError", categoryError);
        return categoryError;
    }

    public String openingHoursValidation(LocalTime localTime, Model model) {
        String openingHoursError = "";
    
        if (localTime == null) {
            openingHoursError = "Please enter opening hours";
        }
    
        model.addAttribute("openingHoursError", openingHoursError);
        return openingHoursError;
    }

    public String closingHoursValidation(LocalTime localTime, Model model) {
        String closingHoursError = "";
    
        if (localTime == null) {
            closingHoursError = "Please enter closing hours";
        }
    
        model.addAttribute("closingHoursError", closingHoursError);
        return closingHoursError;
    }
    

    public String phoneNumberValidation(String rPhoneNumber, Model model) {
        String phoneNumberError = "";
        String phoneNumberRegEx = "^\\d{10}$";
        if (rPhoneNumber == null || rPhoneNumber.trim().length() == 0) {
            phoneNumberError = "Please enter phone number";
        } else if (!rPhoneNumber.matches(phoneNumberRegEx)) {
            phoneNumberError = "Phone number must be 10 digits";
        }
        model.addAttribute("phoneNumberError", phoneNumberError);
        return phoneNumberError;
    }

    public String descriptionValidation(String description, Model model) {
        String descriptionError = "";
        if (description == null || description.trim().length() == 0) {
            descriptionError = "Please enter description";
        } else if (description.length() < 10 || description.length() > 1000) {
            descriptionError = "Description should be between 10 and 1000 characters long";
        }
        model.addAttribute("descriptionError", descriptionError);
        return descriptionError;
    }
    public String otpValidation(String oldOtp, String newOtp, Model model) {
        String otpError = "";
        if (newOtp == null || newOtp.trim().length() == 0) {
            otpError = "Please enter Otp";
        } else if (newOtp.equals(oldOtp) == false) {
            otpError = "Invalid Otp";
        }
        model.addAttribute("otpError", otpError);
        return otpError;
    }
}
