package view;

import controller.GameController;
import java.awt.*;
import javax.swing.*;
import manager.GameManager;
import manager.SoundManager;

public class GameWindow extends JFrame {
    private static final String END_GAME_IMAGE = "images/final_screen.png";
    private GamePanel gamePanel;
    private HUDPanel hudPanel;
    private GameController controller;

    public GameWindow() {
        setTitle("Sokoban - Trabajo Final con Janowski");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initializeComponents();
        initializeSounds();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        // Panel de juego
        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        // HUD
        hudPanel = new HUDPanel();
        add(hudPanel, BorderLayout.NORTH);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(50, 50, 50));

        JButton restartButton = new JButton("Reiniciar (R)");
        restartButton.setFont(new Font("Arial", Font.BOLD, 14));
        restartButton.setFocusable(false);
        restartButton.addActionListener(event -> {
            if (event.getSource() instanceof Component component) {
                component.requestFocusInWindow();
            }
            controller.restartLevel();
        });

        JButton checkpointButton = new JButton("Checkpoint (ESPACIO)");
        checkpointButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkpointButton.setFocusable(false);
        checkpointButton.addActionListener(event -> {
            GameManager.getInstance().restoreCheckpoint();
            refresh();
            if (event.getSource() instanceof Component component) {
                component.requestFocusInWindow();
            }
        });

        buttonPanel.add(restartButton);
        buttonPanel.add(checkpointButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Controller
        controller = new GameController();
        controller.setGameWindow(this);
        addKeyListener(controller);
        setFocusable(true);
    }

    private void initializeSounds() {
        SoundManager soundManager = SoundManager.getInstance();
        // Cargar sonidos desde resources/sounds/
        soundManager.loadSound("move", "sounds/move.wav");
        soundManager.loadSound("victory", "sounds/victory.wav");
        soundManager.loadSound("explosion", "sounds/explosion.wav");
        soundManager.loadSound("checkpoint", "sounds/checkpoint.wav");
    }

    public void refresh() {
        hudPanel.updateStats();
        gamePanel.repaint();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Sokoban", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showCompletionScreen() {
        SwingUtilities.invokeLater(() -> {
            JDialog dialog = new JDialog(this, "¡Juego completado!", true);
            dialog.setLayout(new BorderLayout());

            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            ImageIcon icon = loadCompletionImage();

            if (icon != null) {
                Image image = icon.getImage();
                int width = image.getWidth(null);
                int height = image.getHeight(null);
                if (width > 0 && height > 0) {
                    int maxWidth = 800;
                    int maxHeight = 600;
                    double scale = Math.min((double) maxWidth / width, (double) maxHeight / height);
                    if (scale < 1.0) {
                        int scaledWidth = (int) (width * scale);
                        int scaledHeight = (int) (height * scale);
                        Image scaled = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                        imageLabel.setIcon(new ImageIcon(scaled));
                    } else {
                        imageLabel.setIcon(icon);
                    }
                } else {
                    imageLabel.setIcon(icon);
                }
            } else {
                imageLabel.setText("¡Felicitaciones! Has completado todos los niveles.");
                imageLabel.setForeground(Color.WHITE);
                imageLabel.setFont(new Font("Arial", Font.BOLD, 20));
                imageLabel.setOpaque(true);
                imageLabel.setBackground(new Color(30, 30, 30));
            }

            dialog.add(imageLabel, BorderLayout.CENTER);

            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
            JButton restartButton = new JButton("Empezar desde el nivel 1");
            JButton exitButton = new JButton("Cerrar juego");

            restartButton.addActionListener(event -> {
                if (event.getSource() instanceof Component component) {
                    component.requestFocusInWindow();
                }
                dialog.dispose();
                controller.restartFromBeginning();
                requestFocusInWindow();
            });

            exitButton.addActionListener(event -> {
                if (event.getSource() instanceof Component component) {
                    component.requestFocusInWindow();
                }
                dialog.dispose();
                dispose();
                System.exit(0);
            });

            buttonsPanel.add(restartButton);
            buttonsPanel.add(exitButton);

            dialog.add(buttonsPanel, BorderLayout.SOUTH);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });
    }

    private ImageIcon loadCompletionImage() {
        java.net.URL imgURL = getClass().getClassLoader().getResource(END_GAME_IMAGE);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        }

        java.io.File fallback = new java.io.File("resources/" + END_GAME_IMAGE);
        if (fallback.exists()) {
            return new ImageIcon(fallback.getAbsolutePath());
        }

        return null;
    }
}