package com.npdsoftwares.pdfdownloader.model;

public class PdfResponse {
    private String filename;
    private String pdfBase64;

    public PdfResponse(String filename, String pdfBase64) {
        this.filename = filename;
        this.pdfBase64 = pdfBase64;
    }

    public String getFilename() { return filename; }
    public String getPdfBase64() { return pdfBase64; }
}
