package model.elements.cells;

import model.elements.BoardElement;

// Clase abstracta para celdas
public abstract class Cell extends BoardElement {
    public Cell(int x, int y, boolean solid) {
        super(x, y, solid);
    }

    public boolean isTarget() { return false; }
    public boolean isCheckpoint() { return false; }
    public boolean isSlippery() { return false; }
    public boolean isLock() { return false; }

    // Hook polimórfico llamado cuando una caja entra a esta celda
    public void onBoxEntered(model.elements.boxes.Box box) {
        // por defecto, no hace nada
    }
}