package com.example.productorderservice.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PaymentServiceTest {

    private PaymentService paymentService;
    private PaymentPort paymentPort;
    private PaymentGateway paymentGateway;
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentGateway = new ConsolePaymentGateway();
        paymentRepository = new PaymentRepository();
        paymentPort = new PaymentAdapter(paymentGateway, paymentRepository);
        paymentService = new PaymentService(paymentPort);
    }

    @DisplayName("상품 주문")
    @Test
    void orderProduct() {
        //given
        final PaymentRequest request = PaymentSteps.createPaymentRequest();

        //when
        paymentService.payment(request);
    }

}
