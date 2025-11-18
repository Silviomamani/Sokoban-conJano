package view;

import manager.GameState;
import javax.swing.*;
import java.awt.*;

public class HUDPanel extends JPanel {
    private JLabel movesLabel;
    private JLabel pushesLabel;
    private JLabel levelLabel;

    public HUDPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBackground(new Color(50, 50, 50));
        setPreferredSize(new Dimension(800, 50));

        initializeLabels();
    }

    private void initializeLabels() {
        Font font = new Font("Arial", Font.BOLD, 16);
        Color textColor = Color.WHITE;

        levelLabel = createLabel("Nivel: 1", font, textColor);
        movesLabel = createLabel("Movimientos: 0", font, textColor);
        pushesLabel = createLabel("Empujes: 0", font, textColor);

        add(levelLabel);
        add(movesLabel);
        add(pushesLabel);
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    public void updateStats(GameState state) {
        if (state != null) {
            movesLabel.setText("Movimientos: " + state.getMoves());
            pushesLabel.setText("Empujes: " + state.getPushes());
            levelLabel.setText("Nivel: " + (state.getCurrentLevel() + 1));
        }
    }
}