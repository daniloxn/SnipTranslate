package com.snippingtranslate;

import com.snippingtranslate.background.HotKeyListener;
import com.snippingtranslate.ui.TrayIconManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println("🚀 SnippingTranslate iniciando...\n");
        launch(args);
    }

    @Override
    public void start(Stage primatyStage) {
        // [1] Diz pro JavaFx nao fechar o app quando o painel sumir.
        Platform.setImplicitExit(false);

        // [2] Cria TryIcon (icone na bandeja)
        TrayIconManager trayManager = new TrayIconManager();
        trayManager.showNotification(
                "SnippingTranslate",
                "Rodando em background!\nPressione Ctrl+Shift+T");

        // [2] Registra atalho global
        @SuppressWarnings("unused")
        HotKeyListener hotKeyListener = new HotKeyListener();
        System.out.println("\n✅ Sistema pronto!");
        System.out.println("📌 Ícone adicionado à bandeja");
        System.out.println("⌨️  Atalho: alt+s\n");
    }
}
