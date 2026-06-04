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

// Classe responsável por capturar a tela e permitir seleção de uma região
public class ScreenCaptureService {
    // Retângulo que representa a seleção atual do usuário
    static Rectangle selection;
    // Ponto onde o mouse foi pressionado (início da seleção)
    static Point startPoint;
    // Ponto onde o mouse foi solto ou arrastado (fim da seleção)
    static Point endPoint;
    // Janela atualmente aberta para captura (usada para referência externa)
    public static JWindow activeWindow;

    // ✅ Interface para retornar a imagem quando pronto
    // Callback que será chamado assim que a captura for concluída
    public interface CaptureCallback {
        void onCaptureComplete(BufferedImage image);
    }

    // Armazena o callback fornecido pelo construtor
    private CaptureCallback callback;

    // ✅ Construtor que recebe o callback
    // Inicializa o serviço com um callback para notificar quando a captura for
    // finalizada
    public ScreenCaptureService(CaptureCallback callback) {
        this.callback = callback;
    }

    // Método principal que inicia o processo de captura de tela
    public void captureScreen() {
        try {
            selection = null;
            startPoint = null;
            endPoint = null;
            // Obtém o ambiente gráfico e todos os dispositivos de tela
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] ScreenDevices = ge.getScreenDevices();
            // Calcula os limites totais que englobam todos os monitores
            Rectangle totalBounds = new Rectangle();

            for (GraphicsDevice screenDevice : ScreenDevices) {
                totalBounds = totalBounds.union(screenDevice.getDefaultConfiguration().getBounds());
            }

            // Cria um Robot para capturar a tela
            Robot robot = new Robot();
            // Captura a imagem completa de todos os monitores
            BufferedImage screenShot = robot.createScreenCapture(totalBounds);

            // Painel que desenha a tela capturada e a sobreposição escura
            JPanel panel = new JPanel() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    // Desenha a captura de tela como fundo
                    g2.drawImage(screenShot, 0, 0, null);
                    // Aplica uma camada semi-transparente escura para destacar a seleção
                    g2.setColor(new Color(0, 0, 0, 120));
                    g2.fillRect(0, 0, getWidth(), getHeight());

                    // Se houver uma seleção, desenha a região recortada sem o escurecimento
                    if (selection != null) {
                        g2.drawImage(screenShot,
                                selection.x, selection.y,
                                selection.x + selection.width, selection.y + selection.height,
                                selection.x, selection.y,
                                selection.x + selection.width, selection.y + selection.height,
                                null);
                        // Desenha a borda da seleção
                        // g2.setColor(new Color(0,200,255));
                        g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g2.setPaint(new GradientPaint(selection.x, selection.y, Color.CYAN,
                                selection.x + selection.width, selection.y + selection.height, Color.BLUE));
                        g2.drawRect(selection.x, selection.y, selection.width, selection.height);
                    }
                }
            };

            // Cria uma janela sem decoração para exibir a sobreposição
            JWindow janela = new JWindow();

            // Configura a janela para ficar sempre no topo
            janela.setAlwaysOnTop(true);
            // Permite que a janela receba foco
            janela.setFocusableWindowState(true);
            // Solicita o foco para a janela
            janela.requestFocus();
            // Adiciona o painel à janela
            janela.add(panel);
            // Posiciona a janela cobrindo todos os monitores
            janela.setLocation(totalBounds.x, totalBounds.y);
            janela.setSize(totalBounds.width, totalBounds.height);
            // Torna a janela visível
            janela.setVisible(true);
            // Guarda referência à janela ativa
            activeWindow = janela;

            // Adiciona listener de mouse para capturar cliques e solturas
            panel.addMouseListener(new MouseAdapter() {
                @Override
                // Quando o mouse é pressionado, define o ponto inicial da seleção
                public void mousePressed(MouseEvent e) {
                    startPoint = e.getPoint();
                    endPoint = e.getPoint();
                    // Atualiza o retângulo de seleção (ainda com largura/altura zero)
                    updateSelection();
                    // Redesenha o painel para mostrar a seleção
                    panel.repaint();
                }

                // Quando o mouse é solto, finaliza a seleção
                public void mouseReleased(MouseEvent e) {
                    endPoint = e.getPoint();
                    // Atualiza o retângulo com base nos pontos final
                    updateSelection();
                    // Redesenha o painel
                    panel.repaint();

                    // Se a seleção for válida (maior que zero), processa o recorte
                    if (selection != null && selection.width > 0 && selection.height > 0) {
                        // Recorta a região selecionada da captura original
                        BufferedImage recorte = screenShot.getSubimage(selection.x,
                                selection.y,
                                selection.width,
                                selection.height);

                        // Cria diretório temporário se não existir
                        File tempDir = new File("temp");

                        if (!tempDir.exists()) {
                            tempDir.mkdirs();
                        }
                        // Arquivo para salvar o recorte
                        File imageRecorte = new File(tempDir, "recorte.png");
                        try {
                            // Salva a imagem recortada em disco
                            ImageIO.write(recorte, "png", imageRecorte);
                            System.out.println("✅ Print tirado com sucesso!" + imageRecorte.getAbsolutePath());

                            // ✅ Chama o callback com a imagem
                            // Notifica o callback informando que a captura foi concluída
                            if (callback != null) {
                                callback.onCaptureComplete(recorte);
                            }

                            // Fecha a janela de captura
                            janela.dispose();
                            activeWindow = null;
                        } catch (Exception ex) {
                            // Em caso de erro, fecha a janela e exibe mensagem
                            janela.dispose();
                            activeWindow = null;
                            JOptionPane.showMessageDialog(null, "❌ Erro no processamento: " + ex.getMessage());
                        }
                    }
                }
            });

            // Adiciona listener para arrastar do mouse, atualizando a seleção em tempo real
            panel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    endPoint = e.getPoint();
                    // Atualiza o retângulo de seleção conforme o arrasto
                    updateSelection();
                    // Redesenha o painel para mostrar a seleção em tempo real
                    panel.repaint();
                }
            });

        } catch (AWTException e) {
            // Se ocorrer um erro na captura (ex.: ambiente gráfico não suportado)
            activeWindow = null;
            e.printStackTrace();
        }
    }

    // Método que recalcula o retângulo da seleção com base nos pontos inicial e
    // final
    public void updateSelection() {
        // Se algum ponto for nulo, não faz nada
        if (startPoint == null || endPoint == null)
            return;

        // Calcula coordenadas normalizadas (x, y mínimo e largura/altura absolutos)
        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(startPoint.x - endPoint.x);
        int height = Math.abs(startPoint.y - endPoint.y);

        // Cria o retângulo de seleção
        selection = new Rectangle(x, y, width, height);
    }
}