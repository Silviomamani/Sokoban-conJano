package model;

import model.elements.Player;
import model.elements.boxes.Box;
import model.elements.cells.Cell;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MEMENTO PATTERN: Almacena el estado interno de GameBoard
 * Solo GameBoard (Originator) puede acceder a este estado
 */
public class GameBoardMemento {
    private final Cell[][] grid;
    private final List<Box> boxes;
    private final Player player;
    private final int width;
    private final int height;
    private final int moveCount;
    private final int pushCount;
    private final Map<Integer, List<model.elements.cells.LockCell>> locks;


    GameBoardMemento(Cell[][] grid, List<Box> boxes, Player player, 
                     int width, int height, int moveCount, int pushCount,
                     Map<Integer, List<model.elements.cells.LockCell>> locks) {

        this.width = width;
        this.height = height;
        this.moveCount = moveCount;
        this.pushCount = pushCount;
        

        this.grid = new Cell[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.grid[y][x] = (Cell) grid[y][x].clone();
            }
        }
        

        this.boxes = new ArrayList<>();
        for (Box box : boxes) {
            Box clonedBox = (Box) box.clone();
            this.boxes.add(clonedBox);
        }
        

        this.player = player.clone();
        

        this.locks = new HashMap<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (this.grid[y][x] instanceof model.elements.cells.LockCell) {
                    model.elements.cells.LockCell lock = (model.elements.cells.LockCell) this.grid[y][x];
                    int lockId = lock.getLockId();
                    this.locks.computeIfAbsent(lockId, k -> new ArrayList<>()).add(lock);
                }
            }
        }
    }

    Cell[][] getGrid() {
        return grid;
    }

    List<Box> getBoxes() {
        return boxes;
    }

    Player getPlayer() {
        return player;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    int getMoveCount() {
        return moveCount;
    }

    int getPushCount() {
        return pushCount;
    }

    Map<Integer, List<model.elements.cells.LockCell>> getLocks() {
        return locks;
    }
}

