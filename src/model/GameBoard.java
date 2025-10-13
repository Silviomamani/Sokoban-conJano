package model;

import manager.GameManager;
import model.elements.Direction;
import model.elements.Player;
import model.elements.boxes.*;
import model.elements.cells.Cell;
import model.elements.cells.EmptyCell;
import model.elements.cells.LockCell;
import model.observer.Observer;
import java.util.*;

public class GameBoard implements Observer {
    private Cell[][] grid;
    private List<Box> boxes;
    private Player player;
    private int width;
    private int height;
    private int moveCount;
    private int pushCount;
    private Map<Integer, List<LockCell>> locks; // lockId -> lista de celdas

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
        if (isValidPosition(x, y)) {
            grid[y][x] = cell;
            if (cell instanceof LockCell) {
                LockCell lock = (LockCell) cell;
                locks.computeIfAbsent(lock.getLockId(), k -> new ArrayList<>()).add(lock);
            }
        }
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
            // Intentar empujar la caja
            if (!pushBox(boxAtTarget, dir)) {
                return false;
            }
            pushCount++;
        }

        // Mover jugador
        player.setPosition(newX, newY);
        moveCount++;

        // Verificar checkpoint
        if (targetCell.isCheckpoint()) {
            GameManager.getInstance().saveCheckpoint();
        }

        // Decrementar bombas


        return true;
    }

    private boolean pushBox(Box box, Direction dir) {
        int newX = box.getX() + dir.dx;
        int newY = box.getY() + dir.dy;

        if (!isValidPosition(newX, newY)) return false;

        Cell targetCell = grid[newY][newX];
        if (targetCell.isSolid()) return false;

        if (getBoxAt(newX, newY) != null) return false;

        // Mover caja
        box.setPosition(newX, newY);
        box.onPushed();

        // Notificar a la celda destino que una caja entró (polimórfico, bajo acoplamiento)
        targetCell.onBoxEntered(box);

        // Si es terreno resbaladizo, deslizar
        if (targetCell.isSlippery()) {
            slideBox(box, dir);
        }

        // La interacción específica se maneja en onBoxEntered de cada celda

        return true;
    }

    private void slideBox(Box box, Direction dir) {
        while (true) {
            int nextX = box.getX() + dir.dx;
            int nextY = box.getY() + dir.dy;

            if (!isValidPosition(nextX, nextY)) break;

            Cell nextCell = grid[nextY][nextX];
            if (nextCell.isSolid()) break;
            if (getBoxAt(nextX, nextY) != null) break;

            box.setPosition(nextX, nextY);
            // Notificar a la celda al entrar durante el deslizamiento
            nextCell.onBoxEntered(box);

            if (!nextCell.isSlippery()) break;
        }
    }



    public boolean checkVictory() {
        for (Box box : boxes) {
            if (!box.countsForVictory()) continue;
            Cell cellBelow = grid[box.getY()][box.getX()];
            if (!cellBelow.isTarget()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDefeat() {
        for (Box box : boxes) {
            if (box.isExploded()) {
                return true;
            }
        }
        return false;
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
        if (isValidPosition(x, y)) {
            return grid[y][x];
        }
        return null;
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    // Observer para KeyBox
    @Override
    public void update(Object subject) {
        if (subject instanceof KeyBox) {
            KeyBox keyBox = (KeyBox) subject;
            unlockDoors(keyBox.getKeyId());
        }
    }

    private void unlockDoors(int lockId) {
        List<LockCell> lockCells = locks.get(lockId);
        if (lockCells != null) {
            for (LockCell lock : lockCells) {
                // Reemplazar con celda vacía
                grid[lock.getY()][lock.getX()] = new EmptyCell(lock.getX(), lock.getY());
            }
        }
    }

    // Clonación profunda para checkpoint
    public GameBoard deepClone() {
        GameBoard cloned = new GameBoard(width, height);

        // Clonar grid
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cloned.grid[y][x] = (Cell) this.grid[y][x].clone();
            }
        }

        // Clonar boxes
        for (Box box : boxes) {
            Box clonedBox = (Box) box.clone();
            cloned.boxes.add(clonedBox);
            if (clonedBox instanceof KeyBox) {
                ((KeyBox) clonedBox).addObserver(cloned);
            }
        }

        // Clonar player
        cloned.player = this.player.clone();
        cloned.moveCount = this.moveCount;
        cloned.pushCount = this.pushCount;

        // Reconstruir locks
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (cloned.grid[y][x] instanceof LockCell) {
                    LockCell lock = (LockCell) cloned.grid[y][x];
                    cloned.locks.computeIfAbsent(lock.getLockId(), k -> new ArrayList<>()).add(lock);
                }
            }
        }

        return cloned;
    }

    // Getters
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Player getPlayer() { return player; }
    public List<Box> getBoxes() { return boxes; }
    public int getMoveCount() { return moveCount; }
    public int getPushCount() { return pushCount; }
}
