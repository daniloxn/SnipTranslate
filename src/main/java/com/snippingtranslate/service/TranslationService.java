package com.snippingtranslate.service;
import io.github.cdimascio.dotenv.Dotenv;
import com.deepl.api.DeepLClient;
import com.deepl.api.TextResult;

public class TranslationService {

    public static String translateText(String text) {
        try {
            Dotenv dotenv = Dotenv.load();
            String authKey = dotenv.get("DEEPL_API_KEY");
            if (authKey == null || authKey.isBlank()) {
                throw new IllegalStateException("DEEPL_API_KEY não encontrada no arquivo .env");
            }
            DeepLClient cliente = new DeepLClient(authKey);
            TextResult result = cliente.translateText(text, null, "pt-BR");
            System.out.println(result.getText());
            return result.getText();
            
        } catch (Exception e) {
            System.err.println("❌ Erro na tradução: " + e.getMessage());
            return null; 
        }
    }
}