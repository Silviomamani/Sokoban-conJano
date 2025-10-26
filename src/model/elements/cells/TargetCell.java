package model.elements.cells;

import model.elements.cells.interfaces.Targetable;
import model.elements.boxes.Box;

public class TargetCell extends Cell implements Targetable {

    public TargetCell(int x, int y) {
        super(x, y, false);
    }

    @Override
    public boolean isTargetFor(Box box) {
        return true;
    }

    @Override
    public String getImageName() {
        return "target.png";
    }

    @Override
    public Cell clone() {
        return new TargetCell(x, y);
    }
}