package com.example.homestay.controller;

import com.example.homestay.service.pdf.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/bill")
    public void generatePdf(@RequestParam Integer bookingId, HttpServletResponse response) throws IOException {
        // Call the service to generate the PDF
        pdfService.generateBillPdf(bookingId, response);
    }
}