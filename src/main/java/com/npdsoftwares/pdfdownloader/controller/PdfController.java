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

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    public PdfResponse generatePdf(@RequestBody HtmlPdfRequest request) {
        try {
            String htmlFragment = request.getHtml();
            if (htmlFragment == null || htmlFragment.isEmpty()) {
                throw new IllegalArgumentException("HTML content is empty");
            }

            // --- Wrap plain HTML into well-formed XHTML ---
            Document doc = Jsoup.parse(htmlFragment);
            doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            doc.outputSettings().escapeMode(org.jsoup.nodes.Entities.EscapeMode.xhtml);
            doc.head().prependElement("meta").attr("charset", "UTF-8");
            doc.title("PDF Document");
            String xhtml = doc.html();

            String filename = (request.getFilename() != null && !request.getFilename().isEmpty())
                    ? request.getFilename()
                    : "document.pdf";

            // --- Generate PDF ---
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(xhtml, null);
            builder.toStream(os);
            builder.run();

            String pdfBase64 = Base64.getEncoder().encodeToString(os.toByteArray());

            return new PdfResponse(filename, pdfBase64);

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed: " + e.getMessage(), e);
        }
    }
}
