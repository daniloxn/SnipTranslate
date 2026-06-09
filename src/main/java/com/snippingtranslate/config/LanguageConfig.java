package com.snippingtranslate.config;

/**
 * Configuração de idiomas suportados pela aplicação
 * Contém enums e métodos para gerenciar idiomas de origem e destino
 */
public class LanguageConfig {

    /**
     * Enum dos idiomas de origem suportados para OCR
     */
    public enum SourceLanguage {
        ENGLISH("en", "English (Inglês)"),
        PORTUGUESE("pt", "Portuguese (Português)"),
        SPANISH("es", "Español (Espanhol)"),
        FRENCH("fr", "Français (Francês)"),
        GERMAN("de", "Deutsch (Alemão)"),
        ITALIAN("it", "Italiano");

        public final String code;
        public final String displayName;

        SourceLanguage(String code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }

        public static SourceLanguage fromCode(String code) {
            for (SourceLanguage lang : SourceLanguage.values()) {
                if (lang.code.equals(code)) {
                    return lang;
                }
            }
            return ENGLISH; // Padrão
        }
    }

    /**
     * Enum dos idiomas de destino suportados para tradução
     * Mapeados para código DeepL
     */
    public enum TargetLanguage {
        PORTUGUESE_BR("pt-BR", "Português (Brasil)"),
        PORTUGUESE_PT("pt-PT", "Português (Portugal)"),
        ENGLISH("en-US", "English (Inglês)"),
        SPANISH("es", "Español (Espanhol)"),
        FRENCH("fr", "Français (Francês)"),
        GERMAN("de", "Deutsch (Alemão)"),
        ITALIAN("it", "Italiano"),
        RUSSIAN("ru", "Русский (Russo)"),
        JAPANESE("ja", "日本語 (Japonês)"),
        CHINESE("zh", "中文 (Chinês)");

        public final String code;
        public final String displayName;

        TargetLanguage(String code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }

        public static TargetLanguage fromCode(String code) {
            for (TargetLanguage lang : TargetLanguage.values()) {
                if (lang.code.equals(code)) {
                    return lang;
                }
            }
            return PORTUGUESE_BR; // Padrão
        }
    }

    // Constantes de chaves de configuração
    public static final String CONFIG_KEY_SOURCE_LANG = "source_language";
    public static final String CONFIG_KEY_TARGET_LANG = "target_language";
    public static final String CONFIG_KEY_API_KEY = "deepl_api_key";
    public static final String CONFIG_KEY_THEME = "theme";

    // Valores padrão
    public static final SourceLanguage DEFAULT_SOURCE_LANGUAGE = SourceLanguage.ENGLISH;
    public static final TargetLanguage DEFAULT_TARGET_LANGUAGE = TargetLanguage.PORTUGUESE_BR;
    public static final String DEFAULT_THEME = "light";
}
