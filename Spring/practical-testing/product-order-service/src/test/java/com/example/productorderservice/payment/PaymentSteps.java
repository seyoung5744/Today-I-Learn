package com.example.productorderservice.payment;

import com.example.productorderservice.payment.PaymentRequest;

public class PaymentSteps {

    public static PaymentRequest createPaymentRequest() {
        final Long orderId = 1L;
        final String cardNumber = "1234-1234-1234-1234";
        return new PaymentRequest(orderId, cardNumber);
    }
}
