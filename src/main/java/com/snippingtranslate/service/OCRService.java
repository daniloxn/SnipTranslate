package com.snippingtranslate.service; // Pacote onde a classe está localizada

import java.awt.image.BufferedImage; // Importa a classe para manipular imagens em memória

import net.sourceforge.tess4j.Tesseract; // Importa a biblioteca Tesseract para OCR
import net.sourceforge.tess4j.TesseractException; // Importa exceção específica do Tesseract

public class OCRService { // Classe responsável por realizar o reconhecimento óptico de caracteres (OCR)
    
    private Tesseract tesseract; // Referência para o motor Tesseract que fará a extração do texto
 
    public OCRService() { // Construtor que inicializa o serviço de OCR
        this.tesseract = new Tesseract(); // Cria uma nova instância do Tesseract

        // Pega o diretório onde a aplicação está sendo executada
        String projectDir = System.getProperty("user.dir");

        //Contrói o caminho para a pasta tessdata dentro do projeto
        String tessdataPath = projectDir + "/tessdata";

        // Define o caminho dinamicamente
        tesseract.setDatapath(tessdataPath); // Define o caminho para os dados de idioma do Tesseract
        tesseract.setLanguage("eng"); // Configura o idioma para inglês
    }
    
    // Método público que extrai texto de uma imagem fornecida
    public String extractText(BufferedImage image) {
        try { // Bloco try para capturar exceções do Tesseract
            System.out.println("Extraindo texto."); // Mensagem informativa no console
            
            String text = tesseract.doOCR(image); // Executa o OCR na imagem e armazena o texto reconhecido
            return text; // Retorna o texto extraído
        } catch (TesseractException e) { // Captura exceções específicas do Tesseract
            return "❌ Erro OCR: " + e.getMessage(); // Retorna uma mensagem de erro em caso de falha
        }
    }
}