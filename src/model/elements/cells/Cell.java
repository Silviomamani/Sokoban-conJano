package model.elements.cells;

import model.elements.BoardElement;
import model.elements.boxes.Box;

public abstract class Cell extends BoardElement {

    public Cell(int x, int y, boolean solid) {
        super(x, y, solid);
    }

    public void onBoxEntered(Box box) {
        // Por defecto no hace nada
    }
}