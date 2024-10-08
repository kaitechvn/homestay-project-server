package com.example.homestay.service.pdf;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface PdfService {

    void generateBillPdf(Integer bookingId, String lang, HttpServletResponse response) throws IOException;


}
