package com.snippingtranslate.ui;

import java.awt.*;

import com.snippingtranslate.screen.SettingsPanel;

import javafx.application.Platform;

// Classe responsável por gerenciar o ícone na bandeja do sistema
public class TrayIconManager {
    private SystemTray tray;
    private TrayIcon trayIcon;

    // Construtor: configura e adiciona o ícone à bandeja do sistema
    public TrayIconManager() {
        try {
            // Verifica se sistema operacional suporta system tray
            if (!SystemTray.isSupported()) {
                System.out.println("System Tray não é suportado!");
                return;
            }

            // Obtém acesso à bandeja do sistema
            tray = SystemTray.getSystemTray();

            // Cria ícone (16x16 pixels é o padrão)
            Image image = Toolkit.getDefaultToolkit().getImage("src/main/java/com/snippingtranslate/icons/icon.png"); // Você
                                                                                                                      // cria
                                                                                                                      // uma
                                                                                                                      // imagem

            // Cria popup menu (clique direito no ícone)
            PopupMenu popup = createPopupMenu();

            // Cria TrayIcon com imagem + popup
            trayIcon = new TrayIcon(image, "SnippingTranslate", popup);
            trayIcon.setImageAutoSize(true);

            // Adiciona à bandeja
            tray.add(trayIcon);

            System.out.println("TrayIcon adicionado com sucesso!");

        } catch (AWTException e) {
            System.out.println("Erro ao adicionar TrayIcon: " + e.getMessage());
        }
    }

    // Cria menu que aparece ao clicar direito no ícone
    private PopupMenu createPopupMenu() {
        PopupMenu popup = new PopupMenu();

        MenuItem exitItem = new MenuItem("Sair");
        exitItem.addActionListener(e -> {
            System.out.println("Encerrando aplicação...");
            System.exit(0);
        });

        MenuItem settingsItem = new MenuItem("Configurações");
        settingsItem.addActionListener(e -> Platform.runLater(() -> new SettingsPanel().show()));

        popup.add(settingsItem);
        popup.add(exitItem);
        return popup;
    }

    // Exibe uma notificação na bandeja do sistema
    public void showNotification(String title, String message) {
        if (trayIcon != null) {
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
        }
    }
}