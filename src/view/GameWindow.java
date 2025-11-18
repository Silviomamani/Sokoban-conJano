package view;

import controller.GameController;
import manager.GameManager;
import manager.GameState;
import javax.swing.*;
import java.awt.*;


public class GameWindow extends JFrame implements GameManager.GameStateListener {
    private GamePanel gamePanel;
    private HUDPanel hudPanel;
    private GameController controller;

    public GameWindow() {
        setTitle("Sokoban - Trabajo Final con Janowski");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initializeComponents();
        registerListeners();

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
        add(createButtonPanel(), BorderLayout.SOUTH);

        // Controller
        controller = new GameController();
        controller.setGameWindow(this);
        addKeyListener(controller);
        setFocusable(true);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(50, 50, 50));

        JButton restartButton = createButton("Reiniciar (R)",
                e -> controller.restartLevel());

        JButton checkpointButton = createButton("Checkpoint (ESPACIO)",
                e -> {
                    GameManager.getInstance().restoreCheckpoint();
                    refresh();
                });

        buttonPanel.add(restartButton);
        buttonPanel.add(checkpointButton);

        return buttonPanel;
    }

    private JButton createButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusable(false);
        button.addActionListener(e -> {
            action.actionPerformed(e);
            requestFocusInWindow(); // Devolver foco a ventana
        });
        return button;
    }

    private void registerListeners() {
        GameManager.getInstance().addListener(this);
    }

    // ========== Métodos públicos ==========

    public void refresh() {
        gamePanel.repaint();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Sokoban",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // ========== GameStateListener Implementation ==========

    @Override
    public void onGameStateChanged(GameState state) {
        // Delegar actualización al HUD
        hudPanel.updateStats(state);
    }

    @Override
    public void onLevelCompleted() {
        refresh();
    }

    @Override
    public void onGameCompleted() {
        SwingUtilities.invokeLater(() -> {
            CompletionDialog dialog = new CompletionDialog(this,
                    new CompletionDialog.CompletionDialogListener() {
                        @Override
                        public void onRestartRequested() {
                            controller.restartFromBeginning();
                            requestFocusInWindow();
                        }

                        @Override
                        public void onExitRequested() {
                            dispose();
                            System.exit(0);
                        }
                    });
            dialog.setVisible(true);
        });
    }
}