package com.tma.demo.controller;

import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.request.CreatePaymentRequest;
import com.tma.demo.dto.request.UpdatePaymentStatusRequest;
import com.tma.demo.dto.response.PaymentResponse;
import com.tma.demo.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tma.demo.common.APIConstant.PAYMENT;
import static com.tma.demo.common.APIConstant.PAYMENT_STATUS;

/**
 * PaymentController
 * Version 1.0
 * Date: 22/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 22/10/2024        NGUYEN             create
 */
@RestController
@RequestMapping(value = PAYMENT)
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PutMapping(value = PAYMENT_STATUS)
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePaymentStatus(@RequestBody UpdatePaymentStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.<PaymentResponse>builder()
                .code(HttpStatus.OK.value())
                .message(SuccessMessage.UPDATE_SUCCESS.getMessage())
                .data(paymentService.updatePaymentStatus(request))
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(@RequestBody CreatePaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<PaymentResponse>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(SuccessMessage.CREATED_SUCCESS.getMessage())
                        .data(paymentService.createPayment(request))
                .build());
    }


}
