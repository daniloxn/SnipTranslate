package com.snippingtranslate.background;

import com.snippingtranslate.service.*;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.snippingtranslate.service.OCRService;

import java.util.logging.Level;
import java.util.logging.Logger;



public class HotKeyListener implements NativeKeyListener {

    private static final Logger logger = Logger.getLogger(HotKeyListener.class.getName());

    private boolean ctrlPressed = false;
    private boolean shiftPressed = false;

    public HotKeyListener() {
        try {
            GlobalScreen.registerNativeHook();
            logger.info("Global Hook registrado com sucesso!");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao registrar o global hook");
        }

        GlobalScreen.addNativeKeyListener(this);
        logger.info("NativeKeyListener adicionado!");
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        int keycode = nativeEvent.getKeyCode();
        if (keycode == NativeKeyEvent.VC_SHIFT) {
            shiftPressed = true; 
        }

        if (keycode == NativeKeyEvent.VC_CONTROL) {
            ctrlPressed = true;
        }

        if (ctrlPressed && shiftPressed && keycode == NativeKeyEvent.VC_R) {
            System.out.println("Atalho detectado.");
           
                // captura a tela
                ScreenCaptureService captureService = new ScreenCaptureService((imagemCapturada) -> {
                    System.out.println("Imagem capturada!");

                    new Thread(() -> {
                        OCRService ocr = new OCRService();
                        String texto = ocr.extractText(imagemCapturada);
                        System.out.println("Texto: " + texto);
                        
                        String traduzido = TranslationService.translateText(texto);
                        System.out.println("Traduzido: " + traduzido);

                    }).start();
                });

                captureService.captureScreen();

        }
    }


    public void unregisterHook() {
        try {
            GlobalScreen.unregisterNativeHook();
            logger.info("✅ Global Hook desregistrado");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "❌ Erro ao desregistrar hook", e);
        }
    }
}
