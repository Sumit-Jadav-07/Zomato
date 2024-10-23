package com.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;
import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;

@Service
public class ChargeCreditCard {

    public static PaymentResponse run(CheckoutRequest checkoutRequest) {

        System.out.println("PHASE - 1 - STARTS");

        String apiLoginId = "66r3YfWx";
        String transactionKey = "4W92sc7nG6p7RzFk";
        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        System.out.println("PHASE - 2");

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);

        System.out.println("PHASE - 3");

        // Populate the payment data
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(checkoutRequest.getCardNumber());
        creditCard.setExpirationDate(checkoutRequest.getExpiryDate());
        paymentType.setCreditCard(creditCard);

        System.out.println("PHASE - 4");

        // Set email address (optional)
        CustomerDataType customer = new CustomerDataType();
        customer.setEmail("sumitroyaltechnosoft7@gmail.com");

        System.out.println("PHASE - 5");

        // Create the payment transaction object
        TransactionRequestType txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
        txnRequest.setPayment(paymentType);
        txnRequest.setCustomer(customer);
        txnRequest.setAmount(BigDecimal.valueOf(checkoutRequest.getTotalAmount()).setScale(2, RoundingMode.CEILING));

        System.out.println("PHASE - 6");

        // Create the API request and set the parameters for this specific request
        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setMerchantAuthentication(merchantAuthenticationType);
        apiRequest.setTransactionRequest(txnRequest);

        System.out.println("PHASE - 7");

        // Call the controller
        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();

        System.out.println("PHASE - 8");

        // Get the response
        CreateTransactionResponse response = new CreateTransactionResponse();
        response = controller.getApiResponse();

        System.out.println("PHASE - 9");

        PaymentResponse paymentResponse = new PaymentResponse();

        // Parse the response to determine results
        if (response!=null) {
            // If API Response is OK, go ahead and check the transaction response
            if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
                TransactionResponse result = response.getTransactionResponse();
                if (result.getMessages() != null) {
                    System.out.println("Successfully created transaction with Transaction ID: " + result.getTransId());
                    System.out.println("Response Code: " + result.getResponseCode());
                    System.out.println("Message Code: " + result.getMessages().getMessage().get(0).getCode());
                    System.out.println("Description: " + result.getMessages().getMessage().get(0).getDescription());
                    System.out.println("Auth Code: " + result.getAuthCode());

                    paymentResponse.setResponseCode(result.getResponseCode());
                    paymentResponse.setTransactionId(result.getTransId());
                    paymentResponse.setAuthCode(result.getAuthCode());
                    paymentResponse.setSuccess(true);

                } else {
                    System.out.println("Failed Transaction.");
                    paymentResponse.setSuccess(false);
                    paymentResponse.setMessage("Failed Transaction");
                    if (response.getTransactionResponse().getErrors() != null) {
                        System.out.println("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
                        System.out.println("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
                    }
                }
            } else {
                System.out.println("Failed Transaction.");
                if (response.getTransactionResponse() != null && response.getTransactionResponse().getErrors() != null) {
                    System.out.println("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
                    System.out.println("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
                } else {
                    System.out.println("Error Code: " + response.getMessages().getMessage().get(0).getCode());
                    System.out.println("Error message: " + response.getMessages().getMessage().get(0).getText());
                }
            }
        } else {
            // Display the error code and message when response is null
            ANetApiResponse errorResponse = controller.getErrorResponse();
            System.out.println("Failed to get response");
            if (!errorResponse.getMessages().getMessage().isEmpty()) {
                System.out.println("Error: "+errorResponse.getMessages().getMessage().get(0).getCode()+" \n"+ errorResponse.getMessages().getMessage().get(0).getText());
            }
        }

        System.out.println("PHASE - 10 - FINISHED");
        return paymentResponse;
    }
}
