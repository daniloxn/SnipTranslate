// Define o pacote da classe
package com.snippingtranslate.background;

// Importa as classes de serviço, hook global de teclado e logging
import com.snippingtranslate.service.*;

import javafx.application.Platform;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.github.cdimascio.dotenv.Dotenv;
import com.snippingtranslate.screen.FloatingPanel;
import com.snippingtranslate.screen.SettingsPanel;

// Classe que monitora teclas pressionadas globalmente no sistema operacional
public class HotKeyListener implements NativeKeyListener {

    // Logger para registrar eventos e erros
    private static final Logger logger = Logger.getLogger(HotKeyListener.class.getName());

    // Variável que rastreia se a tecla ALT está sendo pressionada
    private boolean altPressed = false;

    // Construtor que registra o hook global de teclado e adiciona este listener
    public HotKeyListener() {
        try {
            // Registra o hook para capturar eventos de teclado do sistema
            GlobalScreen.registerNativeHook();
            logger.info("Global Hook registrado com sucesso!");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao registrar o global hook");
        }

        // Adiciona este objeto como listener para eventos de teclado
        GlobalScreen.addNativeKeyListener(this);
        logger.info("NativeKeyListener adicionado!");
    }

    // Método chamado quando uma tecla é pressionada
    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        int keycode = nativeEvent.getKeyCode();

        // Se ESC for pressionado, fecha a janela de captura se estiver aberta
        if (keycode == NativeKeyEvent.VC_ESCAPE) {
            if (ScreenCaptureService.activeWindow != null && ScreenCaptureService.activeWindow.isDisplayable()) {
                ScreenCaptureService.activeWindow.dispose();
                ScreenCaptureService.activeWindow = null;
                System.out.println("Captura cancelada via ESC.");
                return;
            }
        }

        // Marca que ALT foi pressionado
        if (keycode == NativeKeyEvent.VC_ALT) {
            altPressed = true;
        }

        // Se ALT + S forem pressionados, inicia o processo de captura e tradução
        if (altPressed && keycode == NativeKeyEvent.VC_S) {
            System.out.println("Atalho detectado.");

            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            String apiKey = dotenv.get("DEEPL_API_KEY");
            if (apiKey == null || apiKey.isEmpty()) {
                Platform.runLater(() -> new SettingsPanel().show());
                return;
            }

            if (ScreenCaptureService.activeWindow != null && ScreenCaptureService.activeWindow.isDisplayable()) {
                ScreenCaptureService.activeWindow.dispose();
                ScreenCaptureService.activeWindow = null;
            }

            // Cria um serviço de captura de tela com callback para quando a imagem for
            // capturada
            ScreenCaptureService captureService = new ScreenCaptureService((imagemCapturada) -> {
                System.out.println("Imagem capturada!");

                // Executa em uma thread separada para não bloquear a interface
                new Thread(() -> {
                    // Extrai texto da imagem usando OCR
                    OCRService ocr = new OCRService();
                    String textocr = ocr.extractText(imagemCapturada);
                    System.out.println("\nTexto: " + textocr);
                    // Verifica se o OCR retornou algum texto
                    if (textocr == "") {
                        Platform.runLater(() -> {
                            FloatingPanel panel = new FloatingPanel();
                            panel.showTranslation("\nNenhum texto encontrado na imagem");
                        });
                    }
                    // Se o OCR encontrou texto, traduz e exibe
                    else {
                        String traduzido = TranslationService.translateText(textocr);
                        System.out.println("\nTraduzido: " + traduzido);

                        Platform.runLater(() -> {
                            FloatingPanel panel = new FloatingPanel();
                            panel.showTranslation(traduzido);
                        });
                    }

                }).start();
            });

            // Inicia a captura de tela
            captureService.captureScreen();
        }
    }

    // Método chamado quando uma tecla é liberada
    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        int keycode = nativeEvent.getKeyCode();

        // Marca que ALT foi liberado
        if (keycode == NativeKeyEvent.VC_ALT) {
            altPressed = false;
        }
    }

    // Método que desregistra o hook global de teclado
    public void unregisterHook() {
        try {
            GlobalScreen.unregisterNativeHook();
            logger.info("✅ Global Hook desregistrado");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "❌ Erro ao desregistrar hook", e);
        }
    }

    public void handlePrintAction() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String apiKey = dotenv.get("DEEPL_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            Platform.runLater(() -> new SettingsPanel().show());
            return;
        }
    }
}