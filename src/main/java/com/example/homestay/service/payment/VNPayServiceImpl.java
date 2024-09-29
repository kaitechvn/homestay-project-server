package com.example.homestay.service.payment;

import com.example.homestay.config.payment.VNPAYConfig;
import com.example.homestay.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.Arrays;

import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class VNPayServiceImpl implements VNPayService {

    private final VNPAYConfig vnpayConfig;

    public String createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnpayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));

        // Choose bank code - if Empty -> Choose in VnPay page
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));


        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnpayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

        // paymentUrl for frontend
        String paymentUrl = vnpayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return paymentUrl;
    }

    @Override
    public void callBackHandler(HttpServletRequest request, HttpServletResponse response) {

        log.info(request.getRequestURI());
        log.info(request.getRequestURL());

        Map<String, String[]> paramMap = request.getParameterMap();

        paramMap.forEach((key, values) -> {
            String valuesString = Arrays.stream(values).collect(Collectors.joining(", "));
            log.info("Key: {}, Value(s): {}", key, valuesString);
        });

        String status = request.getParameter("vnp_ResponseCode");

        if (status.equals("00")) {
            System.out.println("khai dep trai vl");
        }
        else {
            System.out.println("khai hoc gioi vl");
        }
    }

}
