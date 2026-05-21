package com.snippingtranslate;

import com.snippingtranslate.background.HotKeyListener;
import com.snippingtranslate.ui.TrayIconManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 SnippingTranslate iniciando...\n");

        // [1] Cria TrayIcon (ícone na bandeja)
        TrayIconManager trayManager = new TrayIconManager();
        trayManager.showNotification(
            "SnippingTranslate",
            "Rodando em background!\nPressione Ctrl+Shift+T"
        );

        // [2] Registra atalho global
        HotKeyListener hotKeyListener = new HotKeyListener();
        System.out.println("\n✅ Sistema pronto!");
        System.out.println("📌 Ícone adicionado à bandeja");
        System.out.println("⌨️  Atalho: Ctrl+Shift+T\n");

        try {
            Thread.currentThread().join();  // Bloqueia indefinidamente
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
