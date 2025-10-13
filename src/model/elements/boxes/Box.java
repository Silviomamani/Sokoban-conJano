package model.elements.boxes;

import model.elements.BoardElement;

// Clase abstracta para cajas
public abstract class Box extends BoardElement {
    public Box(int x, int y) {
        super(x, y, true);
    }

    public abstract void onPushed();
    public abstract boolean isExploded();

    // Por defecto, una caja cuenta para la condición de victoria
    public boolean countsForVictory() { return true; }
}