package com.example.homestay.service.payment;

import com.example.homestay.config.payment.VNPAYConfig;
import com.example.homestay.dto.reponse.PaymentUrlResponse;
import com.example.homestay.enums.PaymentChannel;
import com.example.homestay.enums.TransactionStatus;
import com.example.homestay.exception.DateConflictException;
import com.example.homestay.exception.ErrorCode;
import com.example.homestay.exception.NotFoundException;
import com.example.homestay.mapper.TransactionMapper;
import com.example.homestay.model.Booking;
import com.example.homestay.model.Transaction;
import com.example.homestay.repository.BookingRepository;
import com.example.homestay.repository.TransactionRepository;
import com.example.homestay.service.booking.BookingService;
import com.example.homestay.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class VNPayServiceImpl implements VNPayService {

    private final BookingRepository bookingRepository;
    private final TransactionRepository transactionRepository;
    private final VNPAYConfig vnpayConfig;
    private final BookingService bookingService;

    public PaymentUrlResponse createVnPayPayment(String ipAddress, long amount, String bankCode, Integer bookingId ) {
        log.info("Starting VnPay payment creation process...");

        String billNo = VNPayUtil.getRandomNumber(8);

        Map<String, String> vnpParamsMap = vnpayConfig.getVNPayConfig();

        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));

        // Choose bank code - if Empty -> Choose in VnPay page
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }

        vnpParamsMap.put("vnp_IpAddr", ipAddress);
        vnpParamsMap.put("vnp_TxnRef",  billNo);


        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnpayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

        // paymentUrl for frontend
        String paymentUrl = vnpayConfig.getVnp_PayUrl() + "?" + queryUrl;
        log.info("Generated VNPay payment URL: {}", paymentUrl);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOOKING_NOT_FOUND));

        Transaction transaction = Transaction.builder()
                .status(TransactionStatus.PENDING)
                .booking(booking)
                .billNo(billNo)
                .channel(PaymentChannel.VNPAY)
                .build();

        transactionRepository.save(transaction);

        return new PaymentUrlResponse(paymentUrl);
    }

    @Override
    public void callBackHandler(HttpServletRequest request, HttpServletResponse response) {
        String redirectUrl;
        log.info("VNPay callback received. Handling transaction...");

        String billNo = request.getParameter("vnp_TxnRef");
        String transNo = request.getParameter("vnp_TransactionNo");
        String method = request.getParameter("vnp_BankCode");
        int amount = Integer.parseInt(request.getParameter("vnp_Amount"));
        String status = request.getParameter("vnp_ResponseCode");

        // Find the existing transaction by billNo (vnp_TxnRef)
        Transaction transaction = transactionRepository.findByBillNo(billNo)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TRANSACTION_NOT_FOUND));

        // Update transaction fields with the callback data
        transaction.setTransNo(transNo);
        transaction.setMethod(method);
        transaction.setAmount(amount);

        Booking bookingReturn = bookingRepository.findById(transaction.getBooking().getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOOKING_NOT_FOUND));

        if (status.equals("00")) {
            try {

                transaction.setStatus(TransactionStatus.COMPLETED);
                bookingService.confirmBooking(bookingReturn.getId());

                // Save the transaction after booking confirmation
                transactionRepository.save(transaction);
                log.info("Transaction completed successfully with ID: {}", transaction.getId());
                redirectUrl = "http://localhost:5173/booking-success";

            } catch (DateConflictException e) {

                transaction.setStatus(TransactionStatus.FAILED);
                transactionRepository.save(transaction);

                // Cancel the booking
                bookingService.cancelBooking(bookingReturn.getId());

                // Log the error
                log.error("Failed to confirm booking with ID: {}. Transaction set to FAILED. Error: {}",
                        bookingReturn.getId(), e.getMessage());

                redirectUrl = "http://localhost:5173/payment-failed?bookingId=" +
                        bookingReturn.getId();
            }

        } else {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);

            redirectUrl = "http://localhost:5173/payment-failed?" +
                    "bookingId=" + bookingReturn.getId() +
                    "&amount=" + bookingReturn.getTotalAmount();

            log.error("Transaction failed with status code: {}", status);
        }


        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException ioException) {
            log.error("Failed to redirect: {}", ioException.getMessage());
        }
    }
 }

