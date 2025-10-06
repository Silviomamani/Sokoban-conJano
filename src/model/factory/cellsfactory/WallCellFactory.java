package model.factory.cellsfactory;

import model.elements.cells.Cell;
import model.elements.cells.WallCell;

public class WallCellFactory extends CellFactory {
    @Override
    public Cell createCell(int x, int y, Object... params) {
        return new WallCell(x, y);
    }
}