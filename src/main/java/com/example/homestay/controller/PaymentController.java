package com.example.homestay.controller;

import com.example.homestay.dto.reponse.PaymentUrlResponse;
//import com.example.homestay.dto.reponse.TransactionResponse;
import com.example.homestay.dto.reponse.TransactionResponse;
import com.example.homestay.service.payment.VNPayService;
import com.example.homestay.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final VNPayService vnPayService;

    @PreAuthorize("@securityUtil.isBookingOwner(authentication.name, #bookingId)")
    @GetMapping("/vnpay")
    public ResponseEntity<PaymentUrlResponse> payByVNPay(HttpServletRequest request,
                                                         @RequestParam Integer bookingId) {
        try {

            String ipAddress = VNPayUtil.getIpAddress(request);
            long amount = Long.parseLong(request.getParameter("amount")) * 100L;
            String bankCode = request.getParameter("bankCode");

            // Pass IP address, amount, and bankCode to the service
            PaymentUrlResponse paymentUrl = vnPayService.createVnPayPayment(ipAddress, amount, bankCode, bookingId);
            return ResponseEntity.ok(paymentUrl);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PaymentUrlResponse("Error creating payment"));
        }
    }

    @GetMapping("/vnpay-callback")
    public void handleVNPayCallback(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        vnPayService.callBackHandler(request, response);
    }

}
