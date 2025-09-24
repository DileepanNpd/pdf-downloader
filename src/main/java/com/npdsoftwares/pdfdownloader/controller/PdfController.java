package com.npdsoftwares.pdfdownloader.controller;

import com.npdsoftwares.pdfdownloader.model.HtmlPdfRequest;
import com.npdsoftwares.pdfdownloader.model.PdfResponse;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @PostMapping("/pdf/generate")
    public ResponseEntity<byte[]> generatePdf(@RequestBody PdfRequest request) {
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
