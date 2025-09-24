package com.npdsoftwares.pdfdownloader.model;

public class HtmlPdfRequest {
    private String html;
    private String filename;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}