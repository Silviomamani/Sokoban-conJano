package model.elements.cells;

import model.elements.cells.interfaces.Slippery;

public class SlipperyCell extends Cell implements Slippery {

    public SlipperyCell(int x, int y) {
        super(x, y, false);
    }

    @Override
    public String getImageName() {
        return "slippery.png";
    }

    @Override
    public Cell clone() {
        return new SlipperyCell(x, y);
    }
}