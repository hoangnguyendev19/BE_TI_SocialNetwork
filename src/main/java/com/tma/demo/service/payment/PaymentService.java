package com.tma.demo.service.payment;

import com.tma.demo.dto.request.CreatePaymentRequest;
import com.tma.demo.dto.request.UpdatePaymentStatusRequest;
import com.tma.demo.dto.response.PaymentResponse;
import org.springframework.stereotype.Service;

/**
 * PaymentService
 * Version 1.0
 * Date: 22/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 22/10/2024        NGUYEN             create
 */
@Service
public interface PaymentService {

    PaymentResponse createPayment(CreatePaymentRequest createPaymentRequest);
    PaymentResponse updatePaymentStatus(UpdatePaymentStatusRequest updatePaymentStatusRequest);
}
