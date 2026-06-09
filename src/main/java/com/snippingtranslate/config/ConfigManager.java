package com.snippingtranslate.config;

import java.io.*;
import java.util.*;

/**
 * Gerenciador central de configurações da aplicação
 * Responsável por carregar, salvar e gerenciar todas as configurações
 */
public class ConfigManager {
    private static final String CONFIG_FILE_NAME = ".sniptranslate-config";
    private static final String CONFIG_FOLDER = System.getProperty("user.home");
    private static final File CONFIG_FILE = new File(CONFIG_FOLDER, CONFIG_FILE_NAME);

    private static ConfigManager instance;
    private Properties properties;

    private ConfigManager() {
        this.properties = new Properties();
        loadConfig();
    }

    /**
     * Obtém a instância singleton do ConfigManager
     */
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /**
     * Carrega as configurações do arquivo .sniptranslate-config
     */
    private void loadConfig() {
        if (CONFIG_FILE.exists()) {
            try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
                properties.load(fis);
                System.out.println("✅ Configurações carregadas de: " + CONFIG_FILE.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("⚠️  Erro ao carregar configurações: " + e.getMessage());
                setDefaults();
            }
        } else {
            System.out.println("📝 Arquivo de configurações não encontrado. Usando padrões.");
            setDefaults();
        }
    }

    /**
     * Define as configurações padrão
     */
    private void setDefaults() {
        properties.setProperty(
                LanguageConfig.CONFIG_KEY_SOURCE_LANG,
                LanguageConfig.DEFAULT_SOURCE_LANGUAGE.code);
        properties.setProperty(
                LanguageConfig.CONFIG_KEY_TARGET_LANG,
                LanguageConfig.DEFAULT_TARGET_LANGUAGE.code);
        properties.setProperty(
                LanguageConfig.CONFIG_KEY_THEME,
                LanguageConfig.DEFAULT_THEME);
    }

    /**
     * Salva todas as configurações no arquivo
     */
    public void saveConfig() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, "SnipTranslate Configuration");
            System.out.println("✅ Configurações salvas com sucesso");
        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar configurações: " + e.getMessage());
        }
    }

    /**
     * Obtém valor de string da configuração
     */
    public String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Obtém valor de string da configuração
     */
    public String getString(String key) {
        return properties.getProperty(key, "");
    }

    /**
     * Define valor de string na configuração
     */
    public void setString(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * Obtém valor booleano da configuração
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        return value == null ? defaultValue : Boolean.parseBoolean(value);
    }

    /**
     * Define valor booleano na configuração
     */
    public void setBoolean(String key, boolean value) {
        properties.setProperty(key, String.valueOf(value));
    }

    /**
     * Obtém idioma de origem selecionado
     */
    public LanguageConfig.SourceLanguage getSourceLanguage() {
        String code = getString(LanguageConfig.CONFIG_KEY_SOURCE_LANG,
                LanguageConfig.DEFAULT_SOURCE_LANGUAGE.code);
        return LanguageConfig.SourceLanguage.fromCode(code);
    }

    /**
     * Define idioma de origem
     */
    public void setSourceLanguage(LanguageConfig.SourceLanguage language) {
        setString(LanguageConfig.CONFIG_KEY_SOURCE_LANG, language.code);
    }

    /**
     * Obtém idioma de destino selecionado
     */
    public LanguageConfig.TargetLanguage getTargetLanguage() {
        String code = getString(LanguageConfig.CONFIG_KEY_TARGET_LANG,
                LanguageConfig.DEFAULT_TARGET_LANGUAGE.code);
        return LanguageConfig.TargetLanguage.fromCode(code);
    }

    /**
     * Define idioma de destino
     */
    public void setTargetLanguage(LanguageConfig.TargetLanguage language) {
        setString(LanguageConfig.CONFIG_KEY_TARGET_LANG, language.code);
    }

    /**
     * Obtém chave da API DeepL
     */
    public String getDeepLApiKey() {
        return getString(LanguageConfig.CONFIG_KEY_API_KEY, "");
    }

    /**
     * Define chave da API DeepL
     */
    public void setDeepLApiKey(String apiKey) {
        if (apiKey != null && !apiKey.isBlank()) {
            setString(LanguageConfig.CONFIG_KEY_API_KEY, apiKey);
        }
    }

    /**
     * Verifica se a chave API foi configurada
     */
    public boolean hasApiKey() {
        return !getDeepLApiKey().isBlank();
    }

    /**
     * Obtém todas as propriedades (para debug)
     */
    public Properties getAll() {
        return new Properties(properties);
    }

    /**
     * Reseta todas as configurações para padrão
     */
    public void resetToDefaults() {
        properties.clear();
        setDefaults();
        saveConfig();
        System.out.println("🔄 Configurações resetadas para padrão");
    }

    /**
     * Exibe as configurações atuais no console (para debug)
     */
    public void printConfig() {
        System.out.println("\n📋 === CONFIGURAÇÕES ATUAIS ===");
        System.out.println("Idioma de Origem: " + getSourceLanguage().displayName);
        System.out.println("Idioma de Destino: " + getTargetLanguage().displayName);
        System.out.println("API Key Configurada: " + (hasApiKey() ? "✅ Sim" : "❌ Não"));
        System.out.println("============================\n");
    }
}
