package com.example.homestay.service.payment;

import com.example.homestay.dto.reponse.PaymentUrlResponse;
import com.example.homestay.dto.reponse.TransactionResponse;
import com.example.homestay.model.Booking;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface VNPayService {

    PaymentUrlResponse createVnPayPayment(String ipAddress, long amount, String bankCode, Integer bookingId);

    void callBackHandler(HttpServletRequest request, HttpServletResponse response);
}
