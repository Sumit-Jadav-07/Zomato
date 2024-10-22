package com.service;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {

    String transactionId;
    String authCode;
    String responseCode;
    String message;
    boolean success;

}
