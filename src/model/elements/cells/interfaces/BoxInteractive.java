package model.elements.cells.interfaces;

import model.elements.boxes.Box;

/**
 * Capacidad de interactuar con cajas que entran
 */
public interface BoxInteractive {
    void onBoxEntered(Box box);
}