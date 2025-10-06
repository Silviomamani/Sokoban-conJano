package model.factory.boxesfactory;

import model.elements.boxes.BombBox;
import model.elements.boxes.Box;

public class BombBoxFactory extends BoxFactory {
    @Override
    public Box createBox(int x, int y, Object... params) {
        return new BombBox(x, y);
    }
}