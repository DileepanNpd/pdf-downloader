package com.npdsoftwares.pdfdownloader.controller;

import com.npdsoftwares.pdfdownloader.model.HtmlPdfRequest;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generatePdf(@RequestBody HtmlPdfRequest request) {
        try {
            String html = "<html><body>" + request.getHtml() + "</body></html>";
    
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();
    
            byte[] pdfBytes = outputStream.toByteArray();
    
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename(request.getFilename())
                    .build());
    
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
