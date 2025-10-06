package model.factory.cellsfactory;

import model.elements.cells.Cell;
import model.elements.cells.TargetCell;

public class TargetCellFactory extends CellFactory {
    @Override
    public Cell createCell(int x, int y, Object... params) {
        return new TargetCell(x, y);
    }
}


