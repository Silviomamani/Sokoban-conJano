package model.factory.cellsfactory;

import model.elements.cells.Cell;
import model.elements.cells.EmptyCell;

public class EmptyCellFactory extends CellFactory {
    @Override
    public Cell createCell(int x, int y, Object... params) {
        return new EmptyCell(x, y);
    }
}
