package com.example.homestay.service.pdf;

import com.example.homestay.dto.reponse.BookingResponse;
import com.example.homestay.service.booking.BookingService;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.IOException;
import java.io.OutputStream;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService{

    private final SpringTemplateEngine templateEngine;
    private final BookingService bookingService;

    @Override
    public void generateBillPdf(Integer bookingId, HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");

        String htmlContent = generateHtml(bookingId);

        ITextRenderer renderer = new ITextRenderer();

        renderer.setDocumentFromString(htmlContent);
        renderer.layout();

        try (OutputStream os = response.getOutputStream()) {
            renderer.createPDF(os);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateHtml(Integer bookingId) {
        BookingResponse bookingResponse = bookingService.getBooking(bookingId);
        Context context = new Context();
        context.setVariable("booking", bookingResponse);
        return templateEngine.process("bill", context);
    }

}
