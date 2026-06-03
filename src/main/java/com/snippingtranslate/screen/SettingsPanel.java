package com.snippingtranslate.screen;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SettingsPanel extends Stage {
    private TextField apiKeyField = new TextField();
    private final File envFile = new File(".env");

    public SettingsPanel() {
        setTitle("Configurações");
        apiKeyField.setPromptText("Insira sua DEEPL_API_KEY");

        Button saveBtn = new Button("Salvar");
        Button cancelBtn = new Button("Cancelar");

        saveBtn.setOnAction(e -> saveKey());
        cancelBtn.setOnAction(e -> close());

        Label apiLabel = new Label("DEEPL API KEY:");

        apiLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        VBox root = new VBox(10, apiLabel, apiKeyField, saveBtn, cancelBtn);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #2d2d2d; -fx-text-fill: white;");

        Scene scene = new Scene(root, 300, 200);
        scene.getStylesheets()
                .add("data:text/css,-fx-base: #2d2d2d; -fx-control-inner-background: #3d3d3d; -fx-text-fill: white;");
        setScene(scene);
    }

    private void saveKey() {
        try {
            List<String> lines = envFile.exists() ? Files.readAllLines(envFile.toPath()) : new ArrayList<>();
            boolean found = false;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith("DEEPL_API_KEY=")) {
                    lines.set(i, "DEEPL_API_KEY=" + apiKeyField.getText());
                    found = true;
                }
            }
            if (!found)
                lines.add("DEEPL_API_KEY=" + apiKeyField.getText());
            Files.write(envFile.toPath(), lines);
            close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
