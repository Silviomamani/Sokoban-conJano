package model.elements.cells.interfaces;

import model.elements.boxes.Box;


public interface Targetable {
    boolean isTargetFor(Box box);
}