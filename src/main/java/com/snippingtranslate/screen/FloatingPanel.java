package com.snippingtranslate.screen;

import com.snippingtranslate.config.ThemeConstants;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Overlay flutuante para exibir resultados da tradução
 * Redesenhado com tema azul bebê e melhor UX
 */
public class FloatingPanel {
    private final Stage stage;
    private final Label resultLabel;
    private final VBox root;

    // Variáveis para guardar a posição do mouse ao arrastar
    private double xOffset = 0;
    private double yOffset = 0;

    public FloatingPanel() {
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);

        root = new VBox(15);
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(ThemeConstants.PADDING_DEFAULT));
        root.setStyle(ThemeConstants.FLOATING_PANEL);
        root.setMaxWidth(500);

        // Permite arrastar a janela
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        // Cabeçalho com título
        HBox header = createHeader();

        // Label do resultado
        resultLabel = new Label("Traduzindo...");
        resultLabel.setStyle(
            "-fx-text-fill: " + ThemeConstants.COLOR_TEXT_DARK + ";" +
            "-fx-font-size: 14px;" +
            "-fx-wrap-text: true;"
        );
        resultLabel.setWrapText(true);

        // VBox para conter o resultado (com padding adicional)
        VBox resultContainer = new VBox();
        resultContainer.setStyle(
            "-fx-background-color: " + ThemeConstants.COLOR_TEXT_LIGHT + ";" +
            "-fx-border-color: " + ThemeConstants.COLOR_PRIMARY_LIGHT + ";" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 12;"
        );
        resultContainer.getChildren().add(resultLabel);

        // Rodapé com botões de ação
        HBox footer = createFooter();

        root.getChildren().addAll(header, resultContainer, footer);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
    }

    /**
     * Cria o cabeçalho da overlay
     */
    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 10, 0));

        Label titleLabel = new Label("✓ Tradução Concluída");
        titleLabel.setStyle(
            "-fx-text-fill: " + ThemeConstants.COLOR_PRIMARY_DARKER + ";" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 14px;"
        );

        header.getChildren().add(titleLabel);
        return header;
    }

    /**
     * Cria rodapé com botões de ação
     */
    private HBox createFooter() {
        HBox footer = new HBox(ThemeConstants.SPACING_NORMAL);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(10, 0, 0, 0));

        // Botão Copiar
        Button copyBtn = new Button("📋 Copiar");
        copyBtn.setStyle(
            "-fx-background-color: " + ThemeConstants.COLOR_PRIMARY + ";" +
            "-fx-text-fill: " + ThemeConstants.COLOR_TEXT_DARK + ";" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 12px;" +
            "-fx-padding: 8 16 8 16;" +
            "-fx-background-radius: 4;" +
            "-fx-cursor: hand;"
        );
        copyBtn.setOnAction(e -> copyToClipboard());

        // Botão Fechar
        Button closeBtn = new Button("✕ Fechar");
        closeBtn.setStyle(
            "-fx-background-color: " + ThemeConstants.COLOR_ERROR + ";" +
            "-fx-text-fill: " + ThemeConstants.COLOR_TEXT_LIGHT + ";" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 12px;" +
            "-fx-padding: 8 16 8 16;" +
            "-fx-background-radius: 4;" +
            "-fx-cursor: hand;"
        );
        closeBtn.setOnAction(e -> hide());

        footer.getChildren().addAll(copyBtn, closeBtn);
        return footer;
    }

    /**
     * Exibe o resultado da tradução
     */
    public void showTranslation(String text) {
        Platform.runLater(() -> {
            resultLabel.setText(text);
            stage.sizeToScene();

            // Posiciona no canto inferior direito da tela
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double x = screenBounds.getMaxX() - stage.getWidth() - 30;
            double y = screenBounds.getMaxY() - stage.getHeight() - 30;

            // Limita tamanho máximo
            if (stage.getWidth() > 550) {
                stage.setWidth(550);
            }
            if (stage.getHeight() > 400) {
                stage.setHeight(400);
            }

            stage.setX(x);
            stage.setY(y);

            root.setOpacity(0);
            stage.show();

            // Animação de entrada
            FadeTransition ft = new FadeTransition(Duration.millis(300), root);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
        });
    }

    /**
     * Copia o resultado para a área de transferência
     */
    private void copyToClipboard() {
        try {
            String text = resultLabel.getText();
            java.awt.Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(
                    new java.awt.datatransfer.StringSelection(text),
                    null
                );
            System.out.println("✅ Texto copiado para a área de transferência");
        } catch (Exception e) {
            System.err.println("❌ Erro ao copiar: " + e.getMessage());
        }
    }

    /**
     * Obtém o texto atualmente exibido
     */
    public String getText() {
        return resultLabel.getText();
    }

    /**
     * Fecha a overlay com animação
     */
    public void hide() {
        FadeTransition ft = new FadeTransition(Duration.millis(300), root);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(e -> stage.hide());
        ft.play();
    }

    /**
     * Obtém o stage da overlay
     */
    public Stage getStage() {
        return stage;
    }
}
