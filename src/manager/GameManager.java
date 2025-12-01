package manager;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import model.GameBoard;

/**
 * SINGLETON: GameManager
 * OBSERVER: Notifica cambios de estado a listeners
 * Principios aplicados:
 * - SRP: Maneja lógica de niveles y estado del juego
 * - Observer: Sistema de notificaciones para desacoplar vista
 */
public class GameManager {
    private static GameManager instance;
    private final List<String> levelFiles;
    private int currentLevelIndex;
    private GameBoard currentBoard;
    private model.GameBoardMemento checkpointMemento;
    private final List<GameStateListener> listeners;

    public interface GameStateListener {
        void onGameStateChanged(GameState state);
        void onLevelCompleted();
        void onGameCompleted();
    }

    private GameManager() {
        levelFiles = new ArrayList<>();
        listeners = new ArrayList<>();
        currentLevelIndex = 0;
        initializeLevels();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }


    public void addListener(GameStateListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(GameStateListener listener) {
        listeners.remove(listener);
    }

    private void notifyStateChanged() {
        GameState state = getCurrentState();
        for (GameStateListener listener : listeners) {
            listener.onGameStateChanged(state);
        }
    }

    private void notifyLevelCompleted() {
        for (GameStateListener listener : listeners) {
            listener.onLevelCompleted();
        }
    }

    private void notifyGameCompleted() {
        for (GameStateListener listener : listeners) {
            listener.onGameCompleted();
        }
    }

    private GameState getCurrentState() {
        if (currentBoard == null) {
            return new GameState(0, 0, 0);
        }
        return new GameState(
                currentBoard.getMoveCount(),
                currentBoard.getPushCount(),
                currentLevelIndex
        );
    }


    private void initializeLevels() {
        try {
            // Intentar cargar desde recursos (JAR)
            loadLevelsFromResources();

            // Si no se encontraron niveles en recursos, intentar desde el sistema de archivos
            if (levelFiles.isEmpty()) {
                loadLevelsFromFileSystem();
            }

            // Ordenar los niveles por nombre para mantener un orden consistente
            Collections.sort(levelFiles);

            // Si aún no hay niveles, crear uno por defecto
            if (levelFiles.isEmpty()) {
                System.err.println("No se encontraron niveles. Usando nivel por defecto.");
                levelFiles.add("levels/level1.txt");
            }

            System.out.println("Niveles cargados: " + levelFiles.size());
        } catch (Exception e) {
            System.err.println("Error al cargar niveles automáticamente: " + e.getMessage());
            // Fallback: agregar niveles por defecto
            if (levelFiles.isEmpty()) {
                levelFiles.add("levels/level1.txt");
            }
        }
    }

    private void loadLevelsFromResources() {
        try {

            java.net.URL resourceUrl = getClass().getClassLoader().getResource("levels");
            if (resourceUrl != null) {
                URI uri = resourceUrl.toURI();
                Path levelsPath;

                if (uri.getScheme().equals("jar")) {

                    FileSystem fileSystem;
                    try {
                        fileSystem = FileSystems.getFileSystem(uri);
                    } catch (FileSystemNotFoundException e) {
                        fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                    }
                    levelsPath = fileSystem.getPath("/levels");
                } else {

                    levelsPath = Paths.get(uri);
                }

                try (Stream<Path> paths = Files.walk(levelsPath)) {
                    paths.filter(Files::isRegularFile)
                            .filter(p -> p.toString().endsWith(".txt"))
                            .forEach(p -> {
                                String fileName = p.getFileName().toString();
                                levelFiles.add("levels/" + fileName);
                            });
                }
            }
        } catch (URISyntaxException | IOException e) {

        }
    }

    private void loadLevelsFromFileSystem() {
        try {

            Path levelsPath = Paths.get(System.getProperty("user.dir"), "resources", "levels");

            if (Files.exists(levelsPath) && Files.isDirectory(levelsPath)) {
                try (Stream<Path> paths = Files.list(levelsPath)) {
                    paths.filter(Files::isRegularFile)
                            .filter(p -> p.toString().endsWith(".txt"))
                            .forEach(p -> {
                                String fileName = p.getFileName().toString();
                                levelFiles.add("levels/" + fileName);
                            });
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer niveles desde el sistema de archivos: " + e.getMessage());
        }
    }



    public void loadLevel(int levelIndex) {
        if (levelIndex >= 0 && levelIndex < levelFiles.size()) {
            currentLevelIndex = levelIndex;
            String filepath = levelFiles.get(levelIndex);
            currentBoard = LevelBuilder.buildLevel(filepath);
            checkpointMemento = null;
            notifyStateChanged();
        }
    }

    public void restartLevel() {
        loadLevel(currentLevelIndex);
    }

    public boolean nextLevel() {
        if (currentLevelIndex < levelFiles.size() - 1) {
            notifyLevelCompleted();
            loadLevel(currentLevelIndex + 1);
            return true;
        } else {
            // Juego completado
            notifyGameCompleted();
            return false;
        }
    }

    public void restartFromFirstLevel() {
        loadLevel(0);
    }


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
            notifyStateChanged(); // Notificar cambio después de restaurar
        }
    }


    public void notifyMovement() {
        notifyStateChanged();
    }

    // ========== Getters ==========

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