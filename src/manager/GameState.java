package manager;

/**
 * Data Transfer Object para el estado del juego
 * Principios aplicados:
 * - Information Expert: Encapsula datos de estado
 * - Immutable: Los datos no cambian después de crearse
 */
public class GameState {
    private final int moves;
    private final int pushes;
    private final int currentLevel;

    public GameState(int moves, int pushes, int currentLevel) {
        this.moves = moves;
        this.pushes = pushes;
        this.currentLevel = currentLevel;
    }

    public int getMoves() {
        return moves;
    }

    public int getPushes() {
        return pushes;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public String toString() {
        return String.format("GameState[level=%d, moves=%d, pushes=%d]",
                currentLevel + 1, moves, pushes);
    }
}