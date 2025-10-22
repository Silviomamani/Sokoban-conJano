package manager;

import model.GameBoard;
import java.util.ArrayList;
import java.util.List;

// SINGLETON: GameManager
public class GameManager {
    private static GameManager instance;
    private List<String> levelFiles;
    private int currentLevelIndex;
    private GameBoard currentBoard;
    private GameBoard checkpointBoard;

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
        levelFiles.add("levels/level6.txt");
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
            checkpointBoard = null;
        }
    }

    public void restartLevel() {
        loadLevel(currentLevelIndex);
    }

    public void nextLevel() {
        if (currentLevelIndex < levelFiles.size() - 1) {
            loadLevel(currentLevelIndex + 1);
        }
    }

    public void saveCheckpoint() {
        if (currentBoard != null) {
            checkpointBoard = currentBoard.deepClone();
        }
    }

    public void restoreCheckpoint() {
        if (checkpointBoard != null) {
            currentBoard = checkpointBoard.deepClone();
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
