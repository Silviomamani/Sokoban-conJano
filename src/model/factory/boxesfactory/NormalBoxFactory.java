package model.factory.boxesfactory;

import model.elements.boxes.Box;
import model.elements.boxes.NormalBox;

public class NormalBoxFactory extends BoxFactory {
    @Override
    public Box createBox(int x, int y, Object... params) {
        return new NormalBox(x, y);
    }
}
