package com.snippingtranslate.service;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class ScreenCaptureService {
    static Rectangle selection;
    static Point startPoint;
    static Point endPoint;

    // ✅ Interface para retornar a imagem quando pronto
    public interface CaptureCallback {
        void onCaptureComplete(BufferedImage image);
    }

    private CaptureCallback callback;

    // ✅ Construtor que recebe o callback
    public ScreenCaptureService(CaptureCallback callback) {
        this.callback = callback;
    }

    public void captureScreen() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] ScreenDevices = ge.getScreenDevices();
            Rectangle totalBounds = new Rectangle();

            for (GraphicsDevice screenDevice : ScreenDevices) {
                totalBounds = totalBounds.union(screenDevice.getDefaultConfiguration().getBounds());
            }

            Robot robot = new Robot();
            BufferedImage screenShot = robot.createScreenCapture(totalBounds);

            JPanel panel = new JPanel() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.drawImage(screenShot, 0, 0, null);
                    g2.setColor(new Color(0, 0, 0, 120));
                    g2.fillRect(0, 0, getWidth(), getHeight());

                    if (selection != null) {
                        g2.drawImage(screenShot,
                            selection.x, selection.y,
                            selection.x + selection.width, selection.y + selection.height,
                            selection.x, selection.y,
                            selection.x + selection.width, selection.y + selection.height,
                            null);

                        g2.setColor(Color.BLACK);
                        g2.drawRect(selection.x, selection.y, selection.width, selection.height);
                    }
                }
            };

            JWindow janela = new JWindow();
            janela.setAlwaysOnTop(true);
            janela.setFocusableWindowState(true);
            janela.requestFocus();
            janela.add(panel);
            janela.setLocation(totalBounds.x, totalBounds.y);
            janela.setSize(totalBounds.width, totalBounds.height);
            janela.setVisible(true);
            

            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    startPoint = e.getPoint();
                    endPoint = e.getPoint();
                    updateSelection();
                    panel.repaint();
                }

                public void mouseReleased(MouseEvent e) {
                    endPoint = e.getPoint();
                    updateSelection();
                    panel.repaint();

                    if (selection != null && selection.width > 0 && selection.height > 0) {
                        BufferedImage recorte = screenShot.getSubimage(selection.x,
                            selection.y,
                            selection.width,
                            selection.height
                        );

                        File imageRecorte = new File("C:/Users/danil/IdeaProjects/SnipTranslate/temp/recorte.png");

                        try {
                            ImageIO.write(recorte, "png", imageRecorte);
                            System.out.println("✅ Print tirado com sucesso!");
                            
                            // ✅ Chama o callback com a imagem
                            if (callback != null) {
                                callback.onCaptureComplete(recorte);
                            }
                            
                            janela.dispose();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "❌ Erro no processamento: " + ex.getMessage());
                        }
                    }
                }
            });

            panel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    endPoint = e.getPoint();
                    updateSelection();
                    panel.repaint();
                }
            });

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void updateSelection() {
        if (startPoint == null || endPoint == null)
            return;

        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(startPoint.x - endPoint.x);
        int height = Math.abs(startPoint.y - endPoint.y);

        selection = new Rectangle(x, y, width, height);
    }
}