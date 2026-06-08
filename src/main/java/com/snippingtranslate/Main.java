package com.snippingtranslate;

import com.snippingtranslate.background.HotKeyListener;
import com.snippingtranslate.screen.ApiKeySetupDialog;
import com.snippingtranslate.service.ConfigService;
import com.snippingtranslate.ui.TrayIconManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Ponto de entrada da aplicação SnipTranslate
 * Inicializa configurações, tray icon, hotkeys e verifica chave API
 */
public class Main extends Application {
    public static void main(String[] args) {
        System.out.println("🚀 SnippingTranslate iniciando...\n");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // [1] Diz ao JavaFX para não fechar o app quando o painel sumir
        Platform.setImplicitExit(false);

        // [2] Inicializa o sistema de configurações
        ConfigService.initializeConfig();

        // [3] Cria TrayIcon (ícone na bandeja)
        TrayIconManager trayManager = new TrayIconManager();
        trayManager.showNotification(
                "SnippingTranslate",
                "Rodando em background!\nPressione Alt+S para capturar");

        // [4] Registra atalho global
        @SuppressWarnings("unused")
        HotKeyListener hotKeyListener = new HotKeyListener();

        // [5] Verifica se a chave API está configurada
        if (!ConfigService.getConfig().hasApiKey()) {
            Platform.runLater(this::showApiKeySetupDialog);
        }

        System.out.println("\n✅ Sistema pronto!");
        System.out.println("📌 Ícone adicionado à bandeja");
        System.out.println("⌨️  Atalho: Alt+S\n");
    }

    /**
     * Exibe o dialog de configuração da API key
     */
    private void showApiKeySetupDialog() {
        ApiKeySetupDialog dialog = new ApiKeySetupDialog();
        dialog.setOnSaveCallback(apiKey -> {
            System.out.println("✅ Chave API configurada com sucesso!");
            return null;
        });
        dialog.show();
    }
}
