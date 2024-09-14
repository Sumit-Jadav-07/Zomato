package com.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class CustomerValidators {

    public String fullNameValidation(String rFullname, Model model) {
        String fullnameError = "";
        String fullnameRegEx = "^[a-zA-Z]+(?: [a-zA-Z]+)*$";
        if (rFullname == null || rFullname.trim().length() == 0) {
            fullnameError = "Please enter fullname";

        } else if (rFullname.matches(fullnameRegEx) == false) {
            fullnameError = "Fullname only contains characters";
        }
        model.addAttribute("fullnameError", fullnameError);
        return fullnameError;
    }

    public String emailValidation(String rEmail, Model model) {
        String emailError = "";
        String emailRegEx = "[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]{2,5}";
        if (rEmail == null || rEmail.trim().length() == 0) {
            emailError = "Please enter email-id";
        } else if (rEmail.matches(emailRegEx) == false) {
            emailError = "Please enter valid email-id";

        }
        model.addAttribute("emailError", emailError);
        return emailError;
    }

    public String passwordValidation(String rPass, Model model) {

        String passError = "";
        if (rPass == null || rPass.trim().length() == 0) {
            passError = "Please Enter Password";
        } else if (rPass.length() < 8 || rPass.length() > 16) {
            passError = "Password should be between 8 and 16 characters long";
        } else if (rPass.matches(".*[0-9].*") == false) {
            passError = "Password must have atleast One Digit";
        } else if (rPass.matches(".*[A-Z].*") == false) {
            passError = "Password must have atleast One Upper Character";
        } else if (rPass.matches(".*[a-z].*") == false) {
            passError = "Password must have atleast One Lower Character";
        } else if (rPass.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*") == false) {
            passError = "Password must have atleast One Special Character";
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

    public String birthDateValidation(String rBirthdate, Model model) {
        String birthdateError = "";
        String birthdateRegEx = "^\\d{2}-\\d{2}-\\d{4}$";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);

        if (rBirthdate == null || rBirthdate.trim().length() == 0) {
            birthdateError = "Please enter birthdate";
        } else if (!rBirthdate.matches(birthdateRegEx)) {
            birthdateError = "Birthdate must be in the format dd-MM-yyyy";
        } else {
            try {
                sdf.parse(rBirthdate);
            } catch (Exception e) {
                birthdateError = "Invalid birthdate";
            }
        }
        model.addAttribute("birthdateError", birthdateError);
        return birthdateError;
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

    public String genderValidation(String rGender, Model model) {
        String genderError = "";
        List<String> validGenders = Arrays.asList("Male", "Female", "Prefer not to say");

        if (rGender == null || rGender.trim().length() == 0) {
            genderError = "Please select your gender";
        } else if (!validGenders.contains(rGender)) {
            genderError = "Invalid gender selection";
        }
        model.addAttribute("genderError", genderError);
        return genderError;
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
