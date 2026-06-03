package com.snippingtranslate.screen;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle(
                "-fx-background-color: rgba(30, 30, 30, 0.9); -fx-background-radius: 15; -fx-border-color: #444; -fx-border-radius: 15;");

        root.setMaxWidth(400);

        // [NOVO] 1. Quando o mouse for pressionado, guarda a posição exata do clique
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        // [NOVO] 2. Quando o mouse for arrastado, move a janela calculando a diferença
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        resultLabel = new Label("Traduzindo...");
        resultLabel.setStyle("-fx-text-fill: #E0E0E0; -fx-font-size: 16px;");
        resultLabel.setWrapText(true);

        Button closeBtn = new Button("Fechar");
        closeBtn.setStyle(
                "-fx-background-color: #ff4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");
        closeBtn.setOnAction(e -> hide());

        root.getChildren().addAll(resultLabel, closeBtn);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
    }

    public void showTranslation(String text) {
        Platform.runLater(() -> {
            resultLabel.setText(text);
            stage.sizeToScene();

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(screenBounds.getMaxX() - stage.getWidth() - 20);
            stage.setY(screenBounds.getMaxY() - stage.getHeight() - 20);

            root.setOpacity(0);
            stage.show();

            FadeTransition ft = new FadeTransition(Duration.millis(300), root);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
        });
    }

    public void hide() {
        FadeTransition ft = new FadeTransition(Duration.millis(300), root);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(e -> stage.hide());
        ft.play();
    }
}