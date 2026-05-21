package com.snippingtranslate.service;

import java.awt.image.BufferedImage;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRService {
    
    private Tesseract tesseract;
 
    public OCRService() {
        this.tesseract = new Tesseract();
        tesseract.setDatapath("C:/Users/danil/IdeaProjects/SnipTranslate/tessdata");
        tesseract.setLanguage("eng");
    }
    
   
    public String extractText(BufferedImage image) {
        try {
            System.out.println("Extraindo texto.");
            
            String text = tesseract.doOCR(image);
            return text;
        } catch (TesseractException e) {
            return "❌ Erro OCR: " + e.getMessage();
        }
    }
}