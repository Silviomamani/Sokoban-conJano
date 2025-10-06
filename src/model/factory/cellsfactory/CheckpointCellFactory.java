package model.factory.cellsfactory;

import model.elements.cells.Cell;
import model.elements.cells.CheckpointCell;

public class CheckpointCellFactory extends CellFactory {
    @Override
    public Cell createCell(int x, int y, Object... params) {
        return new CheckpointCell(x, y);
    }
}