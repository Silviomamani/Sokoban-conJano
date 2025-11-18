package view;

import manager.ResourceManager;
import javax.swing.*;
import java.awt.*;


public class CompletionDialog extends JDialog {
    private static final String END_GAME_IMAGE = "images/final_screen.png";

    public interface CompletionDialogListener {
        void onRestartRequested();
        void onExitRequested();
    }

    public CompletionDialog(JFrame parent, CompletionDialogListener listener) {
        super(parent, "¡Juego completado!", true);
        setLayout(new BorderLayout());

        add(createImagePanel(), BorderLayout.CENTER);
        add(createButtonsPanel(listener), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon icon = ResourceManager.getInstance().loadScaledImage(END_GAME_IMAGE, 800, 600);

        if (icon != null) {
            imageLabel.setIcon(icon);
        } else {
            imageLabel.setText("¡Felicitaciones! Has completado todos los niveles.");
            imageLabel.setForeground(Color.WHITE);
            imageLabel.setFont(new Font("Arial", Font.BOLD, 20));
            imageLabel.setOpaque(true);
            imageLabel.setBackground(new Color(30, 30, 30));
        }

        panel.add(imageLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonsPanel(CompletionDialogListener listener) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton restartButton = new JButton("Empezar desde el nivel 1");
        JButton exitButton = new JButton("Cerrar juego");

        restartButton.addActionListener(e -> {
            dispose();
            listener.onRestartRequested();
        });

        exitButton.addActionListener(e -> {
            dispose();
            listener.onExitRequested();
        });

        panel.add(restartButton);
        panel.add(exitButton);

        return panel;
    }
}
