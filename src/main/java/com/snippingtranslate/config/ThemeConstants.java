package com.snippingtranslate.config;

/**
 * Constantes de tema e cores para a aplicação SnipTranslate
 * Tema predominante: Azul Bebê
 */
public class ThemeConstants {

    // ===== PALETA DE CORES - AZUL BEBÊ =====
    public static final String COLOR_PRIMARY_LIGHT = "#B3E5FC";      // Fundo principal claro
    public static final String COLOR_PRIMARY = "#81D4FA";             // Cor principal
    public static final String COLOR_PRIMARY_DARK = "#4FC3F7";        // Bordas e destaques
    public static final String COLOR_PRIMARY_DARKER = "#29B6F6";      // Botões e interações
    public static final String COLOR_ACCENT = "#00BCD4";              // Destaque especial
    
    public static final String COLOR_TEXT_DARK = "#263238";           // Texto sobre fundo claro
    public static final String COLOR_TEXT_LIGHT = "#ECEFF1";          // Texto sobre fundo escuro
    public static final String COLOR_BACKGROUND = "#ECEFF1";          // Fundo secundário
    
    // Cores adicionais para feedback
    public static final String COLOR_SUCCESS = "#4CAF50";
    public static final String COLOR_ERROR = "#F44336";
    public static final String COLOR_WARNING = "#FF9800";
    
    // ===== ESTILOS CSS =====
    
    /**
     * Estilo para botão primário (azul bebê escuro)
     */
    public static final String BUTTON_PRIMARY = 
        "-fx-background-color: " + COLOR_PRIMARY_DARKER + ";" +
        "-fx-text-fill: " + COLOR_TEXT_LIGHT + ";" +
        "-fx-font-weight: bold;" +
        "-fx-font-size: 13px;" +
        "-fx-padding: 10 20 10 20;" +
        "-fx-background-radius: 6;" +
        "-fx-cursor: hand;" +
        "-fx-effect: dropshadow(gaussian, rgba(41, 182, 246, 0.3), 4, 0, 0, 2);";
    
    /**
     * Estilo para botão secundário (azul bebê mais claro)
     */
    public static final String BUTTON_SECONDARY = 
        "-fx-background-color: " + COLOR_PRIMARY + ";" +
        "-fx-text-fill: " + COLOR_TEXT_DARK + ";" +
        "-fx-font-weight: bold;" +
        "-fx-font-size: 13px;" +
        "-fx-padding: 10 20 10 20;" +
        "-fx-background-radius: 6;" +
        "-fx-cursor: hand;";
    
    /**
     * Estilo para botão de cancelar/fechar (cinza neutro)
     */
    public static final String BUTTON_CANCEL = 
        "-fx-background-color: #90A4AE;" +
        "-fx-text-fill: " + COLOR_TEXT_LIGHT + ";" +
        "-fx-font-weight: bold;" +
        "-fx-font-size: 13px;" +
        "-fx-padding: 10 20 10 20;" +
        "-fx-background-radius: 6;" +
        "-fx-cursor: hand;";
    
    /**
     * Estilo para campo de texto (input)
     */
    public static final String TEXT_FIELD = 
        "-fx-background-color: " + COLOR_TEXT_LIGHT + ";" +
        "-fx-text-fill: " + COLOR_TEXT_DARK + ";" +
        "-fx-border-color: " + COLOR_PRIMARY_DARK + ";" +
        "-fx-border-width: 1;" +
        "-fx-border-radius: 4;" +
        "-fx-background-radius: 4;" +
        "-fx-padding: 8 12 8 12;" +
        "-fx-font-size: 13px;";
    
    /**
     * Estilo para rótulo (label) - texto principal
     */
    public static final String LABEL_PRIMARY = 
        "-fx-text-fill: " + COLOR_TEXT_DARK + ";" +
        "-fx-font-size: 14px;" +
        "-fx-font-weight: bold;";
    
    /**
     * Estilo para rótulo (label) - texto secundário
     */
    public static final String LABEL_SECONDARY = 
        "-fx-text-fill: #546E7A;" +
        "-fx-font-size: 12px;";
    
    /**
     * Estilo para ComboBox (seletor)
     */
    public static final String COMBO_BOX = 
        "-fx-background-color: " + COLOR_TEXT_LIGHT + ";" +
        "-fx-text-fill: " + COLOR_TEXT_DARK + ";" +
        "-fx-border-color: " + COLOR_PRIMARY_DARK + ";" +
        "-fx-border-width: 1;" +
        "-fx-border-radius: 4;" +
        "-fx-background-radius: 4;" +
        "-fx-padding: 8 12 8 12;" +
        "-fx-font-size: 13px;";
    
    /**
     * Estilo para VBox (container principal)
     */
    public static final String CONTAINER_MAIN = 
        "-fx-background-color: " + COLOR_BACKGROUND + ";" +
        "-fx-padding: 30;";
    
    /**
     * Estilo para VBox (container secundário/card)
     */
    public static final String CONTAINER_CARD = 
        "-fx-background-color: " + COLOR_TEXT_LIGHT + ";" +
        "-fx-border-color: " + COLOR_PRIMARY_LIGHT + ";" +
        "-fx-border-width: 1;" +
        "-fx-border-radius: 8;" +
        "-fx-background-radius: 8;" +
        "-fx-padding: 20;";
    
    /**
     * Estilo para separador/divisor
     */
    public static final String SEPARATOR = 
        "-fx-border-color: " + COLOR_PRIMARY_LIGHT + ";" +
        "-fx-border-width: 0 0 1 0;";
    
    /**
     * Estilo para a overlay/painel flutuante (resultado da tradução)
     */
    public static final String FLOATING_PANEL = 
        "-fx-background-color: rgba(227, 242, 253, 0.95);" +
        "-fx-background-radius: 12;" +
        "-fx-border-color: " + COLOR_PRIMARY + ";" +
        "-fx-border-width: 2;" +
        "-fx-border-radius: 12;" +
        "-fx-padding: 20;" +
        "-fx-effect: dropshadow(gaussian, rgba(41, 182, 246, 0.4), 8, 0, 0, 4);";
    
    // ===== DIMENSÕES =====
    public static final int WINDOW_WIDTH_SETTINGS = 500;
    public static final int WINDOW_HEIGHT_SETTINGS = 600;
    public static final int WINDOW_WIDTH_APIKEY = 450;
    public static final int WINDOW_HEIGHT_APIKEY = 500;
    
    // ===== ESPAÇAMENTOS =====
    public static final int SPACING_SMALL = 8;
    public static final int SPACING_NORMAL = 15;
    public static final int SPACING_LARGE = 25;
    public static final int PADDING_DEFAULT = 20;
}
