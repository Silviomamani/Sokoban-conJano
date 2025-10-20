package model.elements.boxes;

import model.elements.BoardElement;
import model.elements.interfaces.Pushable;
import model.elements.interfaces.VictoryRelevant;

public abstract class Box extends BoardElement implements Pushable, VictoryRelevant {
    public Box(int x, int y) {
        super(x, y, true);
    }

    @Override
    public boolean countsForVictory() {
        return true; // Default behavior
    }
}