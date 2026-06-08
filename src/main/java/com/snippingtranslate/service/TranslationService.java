package com.snippingtranslate.service;

import com.deepl.api.DeepLClient;
import com.deepl.api.TextResult;
import com.snippingtranslate.config.LanguageConfig;

/**
 * Serviço de tradução com suporte a múltiplos idiomas
 * Integrado com ConfigService para usar configurações salvas
 */
public class TranslationService {

    /**
     * Traduz um texto usando o idioma de destino configurado
     * O idioma de origem é detectado automaticamente ou usa a configuração salva
     */
    public static String translateText(String text) {
        LanguageConfig.TargetLanguage targetLang = ConfigService.getCurrentTargetLanguage();
        return translateText(text, targetLang);
    }

    /**
     * Traduz um texto para um idioma de destino específico
     */
    public static String translateText(String text, LanguageConfig.TargetLanguage targetLanguage) {
        try {
            // Obtém a chave da API a partir do ConfigManager
            String authKey = ConfigService.getConfig().getDeepLApiKey();

            if (authKey == null || authKey.isBlank()) {
                throw new IllegalStateException("DEEPL_API_KEY não configurada. Configure nas Configurações.");
            }

            // Cria cliente DeepL
            DeepLClient cliente = new DeepLClient(authKey);

            // Detecta automaticamente o idioma de origem (null) e traduz
            TextResult result = cliente.translateText(text, null, targetLanguage.code);

            System.out.println("✅ Tradução concluída para: " + targetLanguage.displayName);
            return result.getText();

        } catch (Exception e) {
            System.err.println("❌ Erro na tradução: " + e.getMessage());
            return null;
        }
    }

    /**
     * Traduz um texto de um idioma de origem para um de destino específico
     */
    public static String translateText(String text, 
                                       LanguageConfig.SourceLanguage sourceLanguage,
                                       LanguageConfig.TargetLanguage targetLanguage) {
        try {
            String authKey = ConfigService.getConfig().getDeepLApiKey();

            if (authKey == null || authKey.isBlank()) {
                throw new IllegalStateException("DEEPL_API_KEY não configurada. Configure nas Configurações.");
            }

            DeepLClient cliente = new DeepLClient(authKey);

            // Traduz com idioma de origem explícito
            TextResult result = cliente.translateText(text, sourceLanguage.code, targetLanguage.code);

            System.out.println("✅ Tradução: " + sourceLanguage.displayName + 
                             " → " + targetLanguage.displayName);
            return result.getText();

        } catch (Exception e) {
            System.err.println("❌ Erro na tradução: " + e.getMessage());
            return null;
        }
    }

    /**
     * Verifica se a chave API está configurada corretamente
     */
    public static boolean isApiKeyConfigured() {
        return ConfigService.getConfig().hasApiKey();
    }

    /**
     * Obtém a mensagem de erro apropriada se não tiver API key
     */
    public static String getApiKeyErrorMessage() {
        return "⚠️  Chave da API DeepL não configurada.\nPor favor, configure nas Configurações.";
    }
}