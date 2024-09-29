package com.example.homestay.controller;

import com.example.homestay.service.payment.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final VNPayService vnPayService;

    @GetMapping("/vn-pay")
    public ResponseEntity<String> payByVNPay(HttpServletRequest request) {
        return ResponseEntity.ok(vnPayService.createVnPayPayment(request));
    }

    @GetMapping("/vn-pay-callback")
    public ResponseEntity<?> handleCallBack(HttpServletRequest request, HttpServletResponse response) {
        vnPayService.callBackHandler(request,response);
        return ResponseEntity.noContent().build();
    }
}
