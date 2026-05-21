package com.snippingtranslate.ui;

import java.awt.*;


public class TrayIconManager {
    private SystemTray tray;
    private TrayIcon trayIcon;

    public TrayIconManager() {
        try {
            // ✅ Verifica se sistema operacional suporta system tray
            if (!SystemTray.isSupported()) {
                System.out.println("❌ System Tray não é suportado!");
                return;
            }

            // Obtém acesso à bandeja do sistema
            tray = SystemTray.getSystemTray();

            // Cria ícone (16x16 pixels é o padrão)
            Image image = Toolkit.getDefaultToolkit().getImage("src/main/java/com/snippingtranslate/icons/icon.png");  // Você cria uma imagem

            // Cria popup menu (clique direito no ícone)
            PopupMenu popup = createPopupMenu();

            // Cria TrayIcon com imagem + popup
            trayIcon = new TrayIcon(image, "SnippingTranslate", popup);
            trayIcon.setImageAutoSize(true);

            // Adiciona à bandeja
            tray.add(trayIcon);

            System.out.println("✅ TrayIcon adicionado com sucesso!");

        } catch (AWTException e) {
            System.out.println("❌ Erro ao adicionar TrayIcon: " + e.getMessage());
        }
    }

    // Cria menu que aparece ao clicar direito no ícone
    private PopupMenu createPopupMenu() {
        PopupMenu popup = new PopupMenu();

        MenuItem exitItem = new MenuItem("Sair");
        exitItem.addActionListener(e -> {
            System.out.println("👋 Encerrando aplicação...");
            System.exit(0);
        });

        popup.add(exitItem);
        return popup;
    }

    public void showNotification(String title, String message) {
        if (trayIcon != null) {
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
        }
    }
}
