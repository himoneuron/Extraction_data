package com.photoScrapper.helper.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration class to bind properties prefixed with "app.ocr".
 * This class is now manually implemented with constructors, getters, and setters.
 */
@ConfigurationProperties(prefix = "app.ocr")
public class OcrProperties {

    /**
     * Path to the Tesseract 'tessdata' folder.
     * e.g., C:\Program Files\Tesseract-OCR\tessdata
     */
    private String tesseractPath;

    /**
     * Default language for OCR.
     */
    private String language = "eng";

    /**
     * Default no-argument constructor.
     */
    public OcrProperties() {
    }

    /**
     * Parameterized constructor for all fields.
     *
     * @param tesseractPath Path to the tessdata folder.
     * @param language      Language code (e.g., "eng").
     */
    public OcrProperties(String tesseractPath, String language) {
        this.tesseractPath = tesseractPath;
        this.language = language;
    }

    // --- Manual Getters and Setters ---

    public String getTesseractPath() {
        return tesseractPath;
    }

    public void setTesseractPath(String tesseractPath) {
        this.tesseractPath = tesseractPath;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "OcrProperties{" +
                "tesseractPath='" + tesseractPath + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}