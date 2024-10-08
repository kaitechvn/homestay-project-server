package com.example.homestay.controller;

import com.example.homestay.service.pdf.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/bill/{bookingId}")
    public void generatePdf(@PathVariable Integer bookingId,
                            @RequestParam String lang,
                            HttpServletResponse response) throws IOException {
        // Call the service to generate the PDF
        pdfService.generateBillPdf(bookingId, lang, response);
    }
}