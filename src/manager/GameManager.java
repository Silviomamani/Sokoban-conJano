package manager;

import java.util.ArrayList;
import java.util.List;
import model.GameBoard;

// SINGLETON: GameManager
public class GameManager {
    private static GameManager instance;
    private final List<String> levelFiles;
    private int currentLevelIndex;
    private GameBoard currentBoard;
    private model.GameBoardMemento checkpointMemento;

    private GameManager() {
        levelFiles = new ArrayList<>();
        currentLevelIndex = 0;
        initializeLevels();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private void initializeLevels() {
        levelFiles.add("levels/level1.txt");
        levelFiles.add("levels/level2.txt");
        levelFiles.add("levels/level3.txt");
        levelFiles.add("levels/level4.txt");
        levelFiles.add("levels/level5.txt");
        levelFiles.add("levels/level6.txt");
    }

    public void loadLevel(int levelIndex) {
        if (levelIndex >= 0 && levelIndex < levelFiles.size()) {
            currentLevelIndex = levelIndex;
            String filepath = levelFiles.get(levelIndex);
            currentBoard = LevelBuilder.buildLevel(filepath);
            checkpointMemento = null;
        }
    }

    public void restartLevel() {
        loadLevel(currentLevelIndex);
    }

    public boolean nextLevel() {
        if (currentLevelIndex < levelFiles.size() - 1) {
            loadLevel(currentLevelIndex + 1);
            return true;
        }
        return false;
    }

    public void restartFromFirstLevel() {
        loadLevel(0);
    }

    /**
     * MEMENTO PATTERN: Guarda el estado actual del tablero como checkpoint
     * El GameManager actúa como Caretaker del patrón Memento
     */
    public void saveCheckpoint() {
        if (currentBoard != null) {
            checkpointMemento = currentBoard.createMemento();
        }
    }

    /**
     * MEMENTO PATTERN: Restaura el estado del tablero desde el checkpoint guardado
     * El GameManager actúa como Caretaker del patrón Memento
     */
    public void restoreCheckpoint() {
        if (checkpointMemento != null && currentBoard != null) {
            currentBoard.restoreFromMemento(checkpointMemento);
        }
    }

    public GameBoard getCurrentBoard() {
        return currentBoard;
    }

    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }

    public int getTotalLevels() {
        return levelFiles.size();
    }
}
