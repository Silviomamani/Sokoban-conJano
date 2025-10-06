package view;

import controller.GameController;
import manager.GameManager;
import manager.SoundManager;
import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
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
        restartButton.addActionListener(e -> {
            controller.restartLevel();
        });

        JButton checkpointButton = new JButton("Checkpoint (ESPACIO)");
        checkpointButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkpointButton.setFocusable(false);
        checkpointButton.addActionListener(e -> {
            GameManager.getInstance().restoreCheckpoint();
            refresh();
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
}