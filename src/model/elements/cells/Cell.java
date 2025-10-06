package model.elements.cells;

import model.elements.BoardElement;

// Clase abstracta para celdas
public abstract class Cell extends BoardElement {
    public Cell(int x, int y, boolean solid) {
        super(x, y, solid);
    }

    public abstract boolean isTarget();
    public abstract boolean isCheckpoint();
    public abstract boolean isSlippery();
    public abstract boolean isLock();
}