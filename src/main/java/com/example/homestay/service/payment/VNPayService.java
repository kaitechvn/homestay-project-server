package com.example.homestay.service.payment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface VNPayService {

    String createVnPayPayment(HttpServletRequest request);

    void callBackHandler(HttpServletRequest request, HttpServletResponse response);
}
