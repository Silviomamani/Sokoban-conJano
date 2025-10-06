package view;

import manager.GameManager;
import model.GameBoard;
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

        movesLabel = new JLabel("Movimientos: 0");
        pushesLabel = new JLabel("Empujes: 0");
        levelLabel = new JLabel("Nivel: 1");

        // Estilo de labels
        Font font = new Font("Arial", Font.BOLD, 16);
        Color textColor = Color.WHITE;

        movesLabel.setFont(font);
        movesLabel.setForeground(textColor);
        pushesLabel.setFont(font);
        pushesLabel.setForeground(textColor);
        levelLabel.setFont(font);
        levelLabel.setForeground(textColor);

        add(levelLabel);
        add(movesLabel);
        add(pushesLabel);
    }

    public void updateStats() {
        GameBoard board = GameManager.getInstance().getCurrentBoard();
        if (board != null) {
            movesLabel.setText("Movimientos: " + board.getMoveCount());
            pushesLabel.setText("Empujes: " + board.getPushCount());
            levelLabel.setText("Nivel: " + (GameManager.getInstance().getCurrentLevelIndex() + 1));
        }
    }
}