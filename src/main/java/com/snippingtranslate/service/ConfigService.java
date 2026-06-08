package com.snippingtranslate.service;

import com.snippingtranslate.config.ConfigManager;
import com.snippingtranslate.config.LanguageConfig;

/**
 * Serviço de gerenciamento de configurações
 * Responsável por sincronizar configurações entre UI e ConfigManager (.sniptranslate-config)
 */
public class ConfigService {

    /**
     * Valida uma chave API da DeepL (verificação básica)
     */
    public static boolean validateApiKey(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            return false;
        }
        
        // A chave deve ter no mínimo 30 caracteres (formato típico de chaves DeepL)
        return apiKey.length() >= 30;
    }

    /**
     * Carrega todas as configurações iniciais
     */
    public static void initializeConfig() {
        ConfigManager config = ConfigManager.getInstance();
        // Carrega as configurações do arquivo .sniptranslate-config
        config.printConfig();
    }

    /**
     * Obtém o objeto ConfigManager para uso na aplicação
     */
    public static ConfigManager getConfig() {
        return ConfigManager.getInstance();
    }

    /**
     * Obtém o idioma de origem atual
     */
    public static LanguageConfig.SourceLanguage getCurrentSourceLanguage() {
        return ConfigManager.getInstance().getSourceLanguage();
    }

    /**
     * Obtém o idioma de destino atual
     */
    public static LanguageConfig.TargetLanguage getCurrentTargetLanguage() {
        return ConfigManager.getInstance().getTargetLanguage();
    }

    /**
     * Define o idioma de origem e salva
     */
    public static void setSourceLanguage(LanguageConfig.SourceLanguage language) {
        ConfigManager config = ConfigManager.getInstance();
        config.setSourceLanguage(language);
        config.saveConfig();
        System.out.println("✅ Idioma de origem alterado para: " + language.displayName);
    }

    /**
     * Define o idioma de destino e salva
     */
    public static void setTargetLanguage(LanguageConfig.TargetLanguage language) {
        ConfigManager config = ConfigManager.getInstance();
        config.setTargetLanguage(language);
        config.saveConfig();
        System.out.println("✅ Idioma de destino alterado para: " + language.displayName);
    }

    /**
     * Define a chave API e salva
     */
    public static void setApiKey(String apiKey) {
        if (validateApiKey(apiKey)) {
            ConfigManager config = ConfigManager.getInstance();
            config.setDeepLApiKey(apiKey);
            config.saveConfig();
            System.out.println("✅ Chave API DeepL salva com sucesso");
        } else {
            System.err.println("❌ Chave API inválida");
        }
    }
}
