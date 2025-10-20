package model;

import manager.GameManager;
import model.elements.Direction;
import model.elements.Player;
import model.elements.boxes.*;
import model.elements.cells.Cell;
import model.elements.cells.EmptyCell;
import model.elements.cells.LockCell;
import model.elements.interfaces.Explosive;
import model.elements.interfaces.Identifiable;
import model.elements.interfaces.VictoryRelevant;
import model.observer.Observer;
import java.util.*;

public class GameBoard implements Observer {
    private Cell[][] grid;
    private List<Box> boxes;
    private Player player;
    private final int width;
    private final int height;
    private int moveCount;
    private int pushCount;
    private final Map<Integer, List<LockCell>> locks;

    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[height][width];
        this.boxes = new ArrayList<>();
        this.locks = new HashMap<>();
        this.moveCount = 0;
        this.pushCount = 0;
        initializeEmptyGrid();
    }

    private void initializeEmptyGrid() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new EmptyCell(x, y);
            }
        }
    }

    public void setCell(int x, int y, Cell cell) {
        if (!isValidPosition(x, y)) return;

        grid[y][x] = cell;
        if (cell instanceof LockCell) {
            registerLockCell((LockCell) cell);
        }
    }

    private void registerLockCell(LockCell lock) {
        locks.computeIfAbsent(lock.getLockId(), k -> new ArrayList<>()).add(lock);
    }

    public void addBox(Box box) {
        boxes.add(box);
        if (box instanceof KeyBox) {
            ((KeyBox) box).addObserver(this);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean movePlayer(Direction dir) {
        if (player == null) return false;

        int newX = player.getX() + dir.dx;
        int newY = player.getY() + dir.dy;

        if (!isValidPosition(newX, newY)) return false;

        Cell targetCell = grid[newY][newX];
        if (targetCell.isSolid()) return false;

        Box boxAtTarget = getBoxAt(newX, newY);

        if (boxAtTarget != null) {
            if (!pushBox(boxAtTarget, dir)) {
                return false;
            }
            pushCount++;
        }

        player.setPosition(newX, newY);
        moveCount++;

        if (targetCell.isCheckpoint()) {
            GameManager.getInstance().saveCheckpoint();
        }

        return true;
    }

    private boolean pushBox(Box box, Direction dir) {
        int newX = box.getX() + dir.dx;
        int newY = box.getY() + dir.dy;

        if (!canPushBoxTo(newX, newY)) return false;

        Cell targetCell = grid[newY][newX];

        moveBoxTo(box, newX, newY, targetCell);

        if (targetCell.isSlippery()) {
            slideBox(box, dir);
        }

        return true;
    }

    private boolean canPushBoxTo(int x, int y) {
        if (!isValidPosition(x, y)) return false;

        Cell targetCell = grid[y][x];
        if (targetCell.isSolid()) return false;

        return getBoxAt(x, y) == null;
    }

    private void moveBoxTo(Box box, int x, int y, Cell targetCell) {
        box.setPosition(x, y);
        box.onPushed();

        // Delegate interaction to cell (polymorphism)
        targetCell.onBoxEntered(box);
    }

    private void slideBox(Box box, Direction dir) {
        while (true) {
            int nextX = box.getX() + dir.dx;
            int nextY = box.getY() + dir.dy;

            if (!canSlideBoxTo(nextX, nextY)) break;

            Cell nextCell = grid[nextY][nextX];
            box.setPosition(nextX, nextY);
            nextCell.onBoxEntered(box);

            if (!nextCell.isSlippery()) break;
        }
    }

    private boolean canSlideBoxTo(int x, int y) {
        if (!isValidPosition(x, y)) return false;

        Cell cell = grid[y][x];
        if (cell.isSolid()) return false;

        return getBoxAt(x, y) == null;
    }

    // REFACTORED: Using VictoryRelevant interface
    public boolean checkVictory() {
        for (Box box : boxes) {
            if (!isBoxOnTarget(box)) {
                return false;
            }
        }
        return true;
    }

    private boolean isBoxOnTarget(Box box) {
        // Only boxes that count for victory matter
        if (box instanceof VictoryRelevant && !((VictoryRelevant) box).countsForVictory()) {
            return true; // Skip this box
        }

        Cell cellBelow = grid[box.getY()][box.getX()];
        return cellBelow.isTarget();
    }

    // REFACTORED: Using Explosive interface
    public boolean checkDefeat() {
        for (Box box : boxes) {
            if (isBoxExploded(box)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoxExploded(Box box) {
        return box instanceof Explosive && ((Explosive) box).isExploded();
    }

    public Box getBoxAt(int x, int y) {
        for (Box box : boxes) {
            if (box.getX() == x && box.getY() == y) {
                return box;
            }
        }
        return null;
    }

    public Cell getCell(int x, int y) {
        return isValidPosition(x, y) ? grid[y][x] : null;
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    // REFACTORED: Using Identifiable interface
    @Override
    public void update(Object subject) {
        if (subject instanceof Identifiable) {
            unlockDoors(((Identifiable) subject).getId());
        }
    }

    private void unlockDoors(int lockId) {
        List<LockCell> lockCells = locks.get(lockId);
        if (lockCells == null) return;

        for (LockCell lock : lockCells) {
            replaceLockWithEmptyCell(lock);
        }
    }

    private void replaceLockWithEmptyCell(LockCell lock) {
        grid[lock.getY()][lock.getX()] = new EmptyCell(lock.getX(), lock.getY());
    }

    public GameBoard deepClone() {
        GameBoard cloned = new GameBoard(width, height);

        cloneGrid(cloned);
        cloneBoxes(cloned);
        clonePlayer(cloned);
        cloneStats(cloned);
        rebuildLocks(cloned);

        return cloned;
    }

    private void cloneGrid(GameBoard target) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                target.grid[y][x] = (Cell) this.grid[y][x].clone();
            }
        }
    }

    private void cloneBoxes(GameBoard target) {
        for (Box box : boxes) {
            Box clonedBox = (Box) box.clone();
            target.boxes.add(clonedBox);
            if (clonedBox instanceof KeyBox) {
                ((KeyBox) clonedBox).addObserver(target);
            }
        }
    }

    private void clonePlayer(GameBoard target) {
        target.player = this.player.clone();
    }

    private void cloneStats(GameBoard target) {
        target.moveCount = this.moveCount;
        target.pushCount = this.pushCount;
    }

    private void rebuildLocks(GameBoard target) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (target.grid[y][x] instanceof LockCell) {
                    LockCell lock = (LockCell) target.grid[y][x];
                    target.registerLockCell(lock);
                }
            }
        }
    }

    // Getters
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Player getPlayer() { return player; }
    public List<Box> getBoxes() { return boxes; }
    public int getMoveCount() { return moveCount; }
    public int getPushCount() { return pushCount; }
}