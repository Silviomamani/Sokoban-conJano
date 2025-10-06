package model.factory.cellsfactory;

import model.elements.cells.Cell;
import model.elements.cells.SlipperyCell;

public class SlipperyCellFactory extends CellFactory {
    @Override
    public Cell createCell(int x, int y, Object... params) {
        return new SlipperyCell(x, y);
    }
}

