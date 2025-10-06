package controller;

import manager.GameManager;
import manager.SoundManager;
import model.GameBoard;
import model.elements.Direction;
import view.GameWindow;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener {
    private GameWindow gameWindow;
    private GameManager gameManager;

    public GameController() {
        this.gameManager = GameManager.getInstance();
        initializeGame();
    }

    private void initializeGame() {
        gameManager.loadLevel(0);
    }

    public void setGameWindow(GameWindow window) {
        this.gameWindow = window;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        GameBoard board = gameManager.getCurrentBoard();
        if (board == null) return;

        boolean moved = false;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                moved = board.movePlayer(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                moved = board.movePlayer(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                moved = board.movePlayer(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                moved = board.movePlayer(Direction.RIGHT);
                break;
            case KeyEvent.VK_SPACE:
                gameManager.restoreCheckpoint();
                SoundManager.getInstance().playSound("checkpoint");
                if (gameWindow != null) {
                    gameWindow.refresh();
                }
                return;
            case KeyEvent.VK_R:
                restartLevel();
                return;
        }

        if (moved) {
            SoundManager.getInstance().playSound("move");

            if (gameWindow != null) {
                gameWindow.refresh();
            }

            // Verificar victoria
            if (board.checkVictory()) {
                SoundManager.getInstance().playSound("victory");
                gameManager.nextLevel();
                if (gameWindow != null) {
                    gameWindow.showMessage("¡Nivel completado!");
                    gameWindow.refresh();
                }
            }

            // Verificar derrota
            if (board.checkDefeat()) {
                SoundManager.getInstance().playSound("explosion");
                if (gameWindow != null) {
                    gameWindow.showMessage("¡Bomba explotó! Reiniciando...");
                }
                restartLevel();
            }
        }
    }

    public void restartLevel() {
        gameManager.restartLevel();
        if (gameWindow != null) {
            gameWindow.refresh();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
