package com.service;

import org.springframework.stereotype.Service;

@Service
public class OtpService {
    
    public String getOtp(){
        String source = "0123456789";
        String otp = "";
        for(int i=0; i<6; i++){
            int randomIndex = (int) (Math.random() * source.length());
            otp += randomIndex;
        }
        return otp;
    }

}
