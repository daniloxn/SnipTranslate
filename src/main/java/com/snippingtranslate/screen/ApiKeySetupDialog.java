package com.snippingtranslate.screen;

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
import java.awt.Desktop;
import java.net.URI;

/**
 * Dialog para configuração da chave API da DeepL
 * Exibido quando a chave não for encontrada no .env
 */
public class ApiKeySetupDialog extends Stage {

    private TextField apiKeyField;
    private Label statusLabel;
    private Callback<String, Void> onSaveCallback;

    public ApiKeySetupDialog() {
        initializeUI();
    }

    /**
     * Interface para callback quando a chave é salva
     */
    public interface Callback<T, R> {
        R call(T param);
    }

    /**
     * Define o callback para quando a chave for salva
     */
    public void setOnSaveCallback(Callback<String, Void> callback) {
        this.onSaveCallback = callback;
    }

    /**
     * Inicializa os componentes da interface
     */
    private void initializeUI() {
        setTitle("Configurar API Key - SnipTranslate");
        setWidth(ThemeConstants.WINDOW_WIDTH_APIKEY);
        setHeight(ThemeConstants.WINDOW_HEIGHT_APIKEY);
        setResizable(false);
        initStyle(StageStyle.DECORATED);
        setOnCloseRequest(e -> {
            if (!ConfigService.getConfig().hasApiKey()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText("API Key não configurada");
                alert.setContentText("O aplicativo precisa da chave API da DeepL para funcionar.\n" +
                        "Deseja realmente fechar?");
                if (alert.showAndWait().isPresent() && alert.getResult() != ButtonType.OK) {
                    e.consume();
                }
            }
        });

        VBox mainContainer = new VBox(ThemeConstants.SPACING_NORMAL);
        mainContainer.setStyle(ThemeConstants.CONTAINER_MAIN);
        mainContainer.setPadding(new Insets(ThemeConstants.PADDING_DEFAULT));
        mainContainer.setFillWidth(true);

        // Header
        VBox header = createHeader();

        // Content com ScrollPane
        VBox content = createContent();
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(250);
        scrollPane.setStyle("-fx-control-inner-background: " + ThemeConstants.COLOR_BACKGROUND + ";");

        // Footer
        HBox footer = createFooter();

        mainContainer.getChildren().addAll(header, scrollPane, footer);
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);

        Scene scene = new Scene(mainContainer);
        setScene(scene);
    }

    /**
     * Cria o cabeçalho
     */
    private VBox createHeader() {
        VBox header = new VBox(ThemeConstants.SPACING_SMALL);

        Label titleLabel = new Label("🔑 Configurar Chave da API DeepL");
        titleLabel.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + ThemeConstants.COLOR_PRIMARY_DARKER + ";");

        Label subtitleLabel = new Label("A chave API é necessária para traduzir textos");
        subtitleLabel.setStyle(ThemeConstants.LABEL_SECONDARY);

        Separator separator = new Separator();
        separator.setStyle(ThemeConstants.SEPARATOR);

        header.getChildren().addAll(titleLabel, subtitleLabel, separator);
        return header;
    }

    /**
     * Cria o conteúdo principal
     */
    private VBox createContent() {
        VBox content = new VBox(ThemeConstants.SPACING_NORMAL);
        content.setPadding(new Insets(5));

        // Card de instrução
        VBox instructionCard = createInstructionCard();

        // Campo de entrada
        VBox inputBox = createInputBox();

        // Card de informações
        VBox infoCard = createInfoCard();

        content.getChildren().addAll(instructionCard, inputBox, infoCard);
        return content;
    }

    /**
     * Cria card de instruções
     */
    private VBox createInstructionCard() {
        VBox card = new VBox(ThemeConstants.SPACING_SMALL);
        card.setStyle(
                "-fx-background-color: " + ThemeConstants.COLOR_PRIMARY_LIGHT + ";" +
                        "-fx-border-color: " + ThemeConstants.COLOR_PRIMARY + ";" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 12;");

        Label instructionLabel = new Label("Como obter sua chave API:");
        instructionLabel.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 12px;" +
                        "-fx-text-fill: " + ThemeConstants.COLOR_TEXT_DARK + ";");

        Label step1 = new Label("1. Acesse https://www.deepl.com/pro-api");
        Label step2 = new Label("2. Crie uma conta gratuita");
        Label step3 = new Label("3. Copie sua chave de autenticação");

        for (Label step : new Label[] { step1, step2, step3 }) {
            step.setStyle(
                    "-fx-font-size: 11px;" +
                            "-fx-text-fill: " + ThemeConstants.COLOR_TEXT_DARK + ";");
            step.setWrapText(true);
        }

        card.getChildren().addAll(instructionLabel, step1, step2, step3);
        return card;
    }

    /**
     * Cria box de entrada
     */
    private VBox createInputBox() {
        VBox box = new VBox(ThemeConstants.SPACING_SMALL);

        Label label = new Label("Chave API DeepL:");
        label.setStyle(ThemeConstants.LABEL_PRIMARY);

        apiKeyField = new TextField();
        apiKeyField.setStyle(ThemeConstants.TEXT_FIELD);
        apiKeyField.setPromptText("Cole sua chave API aqui (ex: xxxxxxxxxxxxxxxx:fx)");
        apiKeyField.setPrefHeight(40);

        statusLabel = new Label("");
        statusLabel.setStyle(
                "-fx-font-size: 11px;" +
                        "-fx-text-fill: " + ThemeConstants.COLOR_TEXT_DARK + ";");
        statusLabel.setWrapText(true);

        box.getChildren().addAll(label, apiKeyField, statusLabel);
        return box;
    }

    /**
     * Cria card de informações
     */
    private VBox createInfoCard() {
        VBox card = new VBox(ThemeConstants.SPACING_SMALL);
        card.setStyle(
                "-fx-background-color: " + ThemeConstants.COLOR_BACKGROUND + ";" +
                        "-fx-border-color: " + ThemeConstants.COLOR_PRIMARY_LIGHT + ";" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 10;");

        Label infoLabel = new Label("ℹ️  A chave será armazenada de forma segura no arquivo de configuração");
        infoLabel.setStyle(
                "-fx-font-size: 11px;" +
                        "-fx-text-fill: #546E7A;");
        infoLabel.setWrapText(true);

        card.getChildren().add(infoLabel);
        return card;
    }

    /**
     * Cria rodapé com botões
     */
    private HBox createFooter() {
        HBox footer = new HBox(ThemeConstants.SPACING_NORMAL);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(ThemeConstants.SPACING_NORMAL, 0, 0, 0));

        // Botão para abrir página da DeepL
        Button getKeyBtn = new Button("📖 Obter Chave");
        getKeyBtn.setStyle(ThemeConstants.BUTTON_SECONDARY);
        getKeyBtn.setPrefWidth(110);
        getKeyBtn.setOnAction(e -> openDeepLWebsite());

        // Botão Cancelar
        Button cancelBtn = new Button("Cancelar");
        cancelBtn.setStyle(ThemeConstants.BUTTON_CANCEL);
        cancelBtn.setPrefWidth(100);
        cancelBtn.setOnAction(e -> close());

        // Botão Confirmar
        Button confirmBtn = new Button("Confirmar");
        confirmBtn.setStyle(ThemeConstants.BUTTON_PRIMARY);
        confirmBtn.setPrefWidth(100);
        confirmBtn.setOnAction(e -> saveApiKey());

        footer.getChildren().addAll(getKeyBtn, cancelBtn, confirmBtn);
        return footer;
    }

    /**
     * Valida e salva a chave API
     */
    private void saveApiKey() {
        String apiKey = apiKeyField.getText().trim();

        if (apiKey.isEmpty()) {
            statusLabel.setText("❌ Por favor, insira uma chave API");
            statusLabel.setStyle("-fx-text-fill: " + ThemeConstants.COLOR_ERROR + ";");
            return;
        }

        if (!ConfigService.validateApiKey(apiKey)) {
            statusLabel.setText("❌ Chave API inválida. Deve ter no mínimo 30 caracteres");
            statusLabel.setStyle("-fx-text-fill: " + ThemeConstants.COLOR_ERROR + ";");
            return;
        }

        try {
            ConfigService.setApiKey(apiKey);
            statusLabel.setText("✅ Chave API salva com sucesso!");
            statusLabel.setStyle("-fx-text-fill: " + ThemeConstants.COLOR_SUCCESS + ";");

            if (onSaveCallback != null) {
                onSaveCallback.call(apiKey);
            }

            // Fecha após 1.5 segundo
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    javafx.application.Platform.runLater(this::close);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            statusLabel.setText("❌ Erro ao salvar: " + e.getMessage());
            statusLabel.setStyle("-fx-text-fill: " + ThemeConstants.COLOR_ERROR + ";");
        }
    }

    /**
     * Abre o site da DeepL para obter uma chave
     */
    private void openDeepLWebsite() {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("https://www.deepl.com/pro-api"));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Abrir Website");
            alert.setHeaderText("Abra o link no navegador");
            alert.setContentText("Visite: https://www.deepl.com/pro-api");
            alert.showAndWait();
        }
    }
}
