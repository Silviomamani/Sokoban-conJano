package controller;

import manager.GameManager;
import manager.SoundManager;
import model.GameBoard;
import model.elements.Direction;
import view.GameWindow;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class GameController implements KeyListener {
    private GameWindow gameWindow;
    private GameManager gameManager;
    private final Map<Integer, Runnable> keyActions = new HashMap<>();

    public GameController() {
        this.gameManager = GameManager.getInstance();
        initializeGame();
        initializeKeyActions();
    }

    private void initializeGame() {
        gameManager.loadLevel(0);
    }

    public void setGameWindow(GameWindow window) {
        this.gameWindow = window;
    }

    private void initializeKeyActions() {
        keyActions.put(KeyEvent.VK_UP, () -> move(Direction.UP));
        keyActions.put(KeyEvent.VK_DOWN, () -> move(Direction.DOWN));
        keyActions.put(KeyEvent.VK_LEFT, () -> move(Direction.LEFT));
        keyActions.put(KeyEvent.VK_RIGHT, () -> move(Direction.RIGHT));
        keyActions.put(KeyEvent.VK_SPACE, this::restoreCheckpoint);
        keyActions.put(KeyEvent.VK_R, this::restartLevel);
    }

    private void move(Direction dir) {
        GameBoard board = gameManager.getCurrentBoard();
        if (board == null) return;
        boolean moved = board.movePlayer(dir);
        postMove(moved, board);
    }

    private void restoreCheckpoint() {
        gameManager.restoreCheckpoint();
        SoundManager.getInstance().playSound("checkpoint");
        if (gameWindow != null) gameWindow.refresh();
    }

    private void postMove(boolean moved, GameBoard board) {
        if (!moved) return;
        SoundManager.getInstance().playSound("move");
        if (gameWindow != null) {
            gameWindow.refresh();
        }
        if (board.checkVictory()) {
            SoundManager.getInstance().playSound("victory");
            gameManager.nextLevel();
            if (gameWindow != null) {
                gameWindow.showMessage("¡Nivel completado!");
                gameWindow.refresh();
            }
        }
        if (board.checkDefeat()) {
            SoundManager.getInstance().playSound("explosion");
            if (gameWindow != null) {
                gameWindow.showMessage("¡Bomba explotó! Reiniciando...");
            }
            restartLevel();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Runnable action = keyActions.get(e.getKeyCode());
        if (action != null) action.run();
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
