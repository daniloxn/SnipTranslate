package com.snippingtranslate.screen;

import com.snippingtranslate.config.LanguageConfig;
import com.snippingtranslate.config.ThemeConstants;
import com.snippingtranslate.service.ConfigService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Tela de Configurações redesenhada com tema azul bebê
 * Permite configurar idiomas de origem, destino e opções gerais
 */
public class SettingsPanel extends Stage {

    private ComboBox<LanguageConfig.SourceLanguage> sourceLanguageCombo;
    private ComboBox<LanguageConfig.TargetLanguage> targetLanguageCombo;
    private Label statusLabel;

    public SettingsPanel() {
        initializeUI();
        setupStyles();
    }

    /**
     * Inicializa os componentes da interface
     */
    private void initializeUI() {
        setTitle("Configurações - SnipTranslate");
        setWidth(ThemeConstants.WINDOW_WIDTH_SETTINGS);
        setHeight(ThemeConstants.WINDOW_HEIGHT_SETTINGS);
        setResizable(false);
        initStyle(StageStyle.DECORATED);

        // Container principal
        VBox mainContainer = new VBox(ThemeConstants.SPACING_NORMAL);
        mainContainer.setStyle(ThemeConstants.CONTAINER_MAIN);

        // Cabeçalho
        VBox header = createHeader();

        // Conteúdo
        VBox content = createContent();

        // Rodapé com botões
        HBox footer = createFooter();

        // ScrollPane para conteúdo (caso fique grande)
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-control-inner-background: " + ThemeConstants.COLOR_BACKGROUND + ";");

        // Monta a hierarquia
        mainContainer.getChildren().addAll(header, scrollPane, footer);
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);

        Scene scene = new Scene(mainContainer);
        setScene(scene);
    }

    /**
     * Cria o cabeçalho da janela
     */
    private VBox createHeader() {
        VBox header = new VBox(ThemeConstants.SPACING_SMALL);
        header.setPadding(new Insets(0, 0, ThemeConstants.SPACING_NORMAL, 0));

        Label titleLabel = new Label("⚙️  Configurações");
        titleLabel.setStyle(
            "-fx-font-size: 24px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + ThemeConstants.COLOR_PRIMARY_DARKER + ";"
        );

        Label subtitleLabel = new Label("Personalize o comportamento do SnipTranslate");
        subtitleLabel.setStyle(ThemeConstants.LABEL_SECONDARY);

        Separator separator = new Separator();
        separator.setStyle(ThemeConstants.SEPARATOR);

        header.getChildren().addAll(titleLabel, subtitleLabel, separator);
        return header;
    }

    /**
     * Cria o conteúdo principal com as opções
     */
    private VBox createContent() {
        VBox content = new VBox(ThemeConstants.SPACING_LARGE);
        content.setPadding(new Insets(ThemeConstants.PADDING_DEFAULT));

        // Seção de Idiomas
        VBox languageSection = createLanguageSection();

        // Seção de Informações
        VBox infoSection = createInfoSection();

        content.getChildren().addAll(languageSection, infoSection);
        return content;
    }

    /**
     * Cria a seção de seleção de idiomas
     */
    private VBox createLanguageSection() {
        VBox section = new VBox(ThemeConstants.SPACING_NORMAL);
        section.setStyle(ThemeConstants.CONTAINER_CARD);

        Label sectionTitle = new Label("🌐 Configuração de Idiomas");
        sectionTitle.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + ThemeConstants.COLOR_PRIMARY_DARKER + ";"
        );

        // Idioma de Origem
        VBox sourceBox = createLanguageBox(
            "Idioma de Origem",
            "Selecione o idioma do texto a ser capturado"
        );

        sourceLanguageCombo = new ComboBox<>();
        sourceLanguageCombo.setStyle(ThemeConstants.COMBO_BOX);
        sourceLanguageCombo.setPrefWidth(Double.MAX_VALUE);
        sourceLanguageCombo.getItems().addAll(LanguageConfig.SourceLanguage.values());
        sourceLanguageCombo.setValue(ConfigService.getCurrentSourceLanguage());

        sourceBox.getChildren().add(sourceLanguageCombo);

        // Espaço entre as seções
        Separator separator = new Separator();
        separator.setStyle(ThemeConstants.SEPARATOR);

        // Idioma de Destino
        VBox targetBox = createLanguageBox(
            "Idioma de Destino",
            "Selecione o idioma para o qual o texto será traduzido"
        );

        targetLanguageCombo = new ComboBox<>();
        targetLanguageCombo.setStyle(ThemeConstants.COMBO_BOX);
        targetLanguageCombo.setPrefWidth(Double.MAX_VALUE);
        targetLanguageCombo.getItems().addAll(LanguageConfig.TargetLanguage.values());
        targetLanguageCombo.setValue(ConfigService.getCurrentTargetLanguage());

        targetBox.getChildren().add(targetLanguageCombo);

        section.getChildren().addAll(sectionTitle, sourceBox, separator, targetBox);
        return section;
    }

    /**
     * Helper para criar um box de seleção de idioma
     */
    private VBox createLanguageBox(String title, String description) {
        VBox box = new VBox(ThemeConstants.SPACING_SMALL);

        Label titleLabel = new Label(title);
        titleLabel.setStyle(ThemeConstants.LABEL_PRIMARY);

        Label descLabel = new Label(description);
        descLabel.setStyle(ThemeConstants.LABEL_SECONDARY);
        descLabel.setWrapText(true);

        box.getChildren().addAll(titleLabel, descLabel);
        return box;
    }

    /**
     * Cria a seção de informações
     */
    private VBox createInfoSection() {
        VBox section = new VBox(ThemeConstants.SPACING_NORMAL);
        section.setStyle(
            "-fx-background-color: " + ThemeConstants.COLOR_PRIMARY_LIGHT + ";" +
            "-fx-border-color: " + ThemeConstants.COLOR_PRIMARY_DARK + ";" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 15;"
        );

        Label infoTitle = new Label("ℹ️  Informações");
        infoTitle.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + ThemeConstants.COLOR_TEXT_DARK + ";"
        );

        statusLabel = new Label("Status: Configurações carregadas");
        statusLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-text-fill: " + ThemeConstants.COLOR_TEXT_DARK + ";" +
            "-fx-wrap-text: true;"
        );
        statusLabel.setWrapText(true);

        Label noteLabel = new Label("As alterações são salvas automaticamente ao clicar em 'Salvar'.");
        noteLabel.setStyle(ThemeConstants.LABEL_SECONDARY);

        section.getChildren().addAll(infoTitle, statusLabel, noteLabel);
        return section;
    }

    /**
     * Cria os botões de rodapé
     */
    private HBox createFooter() {
        HBox footer = new HBox(ThemeConstants.SPACING_NORMAL);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(ThemeConstants.SPACING_NORMAL, 0, 0, 0));

        // Botão Cancelar
        Button cancelBtn = new Button("Cancelar");
        cancelBtn.setStyle(ThemeConstants.BUTTON_CANCEL);
        cancelBtn.setPrefWidth(100);
        cancelBtn.setOnAction(e -> close());

        // Botão Resetar
        Button resetBtn = new Button("Resetar");
        resetBtn.setStyle(ThemeConstants.BUTTON_SECONDARY);
        resetBtn.setPrefWidth(100);
        resetBtn.setOnAction(e -> resetToDefaults());

        // Botão Salvar
        Button saveBtn = new Button("Salvar");
        saveBtn.setStyle(ThemeConstants.BUTTON_PRIMARY);
        saveBtn.setPrefWidth(100);
        saveBtn.setOnAction(e -> saveSettings());

        footer.getChildren().addAll(cancelBtn, resetBtn, saveBtn);
        return footer;
    }

    /**
     * Aplica estilos CSS globais
     */
    private void setupStyles() {
        String css = 
            ".combo-box { " + ThemeConstants.COMBO_BOX + " }" +
            ".combo-box-popup .list-view { -fx-background-color: " + ThemeConstants.COLOR_TEXT_LIGHT + "; }" +
            ".combo-box-popup .list-view .list-cell { -fx-text-fill: " + ThemeConstants.COLOR_TEXT_DARK + "; }" +
            ".combo-box-popup .list-view .list-cell:filled:selected { -fx-background-color: " + ThemeConstants.COLOR_PRIMARY + "; }";
        
        getScene().getStylesheets().add("data:text/css," + css.replace(" ", "%20"));
    }

    /**
     * Salva as configurações e fecha a janela
     */
    private void saveSettings() {
        try {
            LanguageConfig.SourceLanguage sourceLanguage = sourceLanguageCombo.getValue();
            LanguageConfig.TargetLanguage targetLanguage = targetLanguageCombo.getValue();

            if (sourceLanguage != null) {
                ConfigService.setSourceLanguage(sourceLanguage);
            }

            if (targetLanguage != null) {
                ConfigService.setTargetLanguage(targetLanguage);
            }

            statusLabel.setText("✅ Configurações salvas com sucesso!");
            
            // Fecha a janela após 1 segundo
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    javafx.application.Platform.runLater(this::close);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            statusLabel.setText("❌ Erro ao salvar configurações: " + e.getMessage());
            System.err.println("Erro ao salvar configurações: " + e.getMessage());
        }
    }

    /**
     * Reseta as configurações para o padrão
     */
    private void resetToDefaults() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Reset");
        alert.setHeaderText("Resetar configurações?");
        alert.setContentText("Todas as configurações serão resetadas para os valores padrão.");

        if (alert.showAndWait().isPresent() && alert.getResult() == ButtonType.OK) {
            ConfigService.getConfig().resetToDefaults();
            
            sourceLanguageCombo.setValue(ConfigService.getCurrentSourceLanguage());
            targetLanguageCombo.setValue(ConfigService.getCurrentTargetLanguage());
            
            statusLabel.setText("✅ Configurações resetadas para o padrão");
        }
    }
}
