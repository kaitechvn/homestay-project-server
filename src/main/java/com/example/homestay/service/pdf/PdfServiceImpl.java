package com.example.homestay.service.pdf;


import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class PdfServiceImpl implements PdfService{

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void generateBillPdf(Integer bookingId, HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=bill.pdf");

        // Generate HTML content from Thymeleaf template
        String htmlContent = generateHtml(bookingId);

        // Create a PDF renderer
        ITextRenderer renderer = new ITextRenderer();

        // Set the content to the renderer
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();

        // Write the PDF to the response output stream
        try (OutputStream os = response.getOutputStream()) {
            renderer.createPDF(os);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateHtml(Integer bookingId) {
        // Create a context and set the variables
        Context context = new Context();
        context.setVariable("bookingId", bookingId);
        return templateEngine.process("bill", context);
    }

}
