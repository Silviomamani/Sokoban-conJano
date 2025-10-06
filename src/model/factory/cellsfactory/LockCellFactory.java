package model.factory.cellsfactory;

import model.elements.cells.Cell;
import model.elements.cells.LockCell;

public class LockCellFactory extends CellFactory {
    @Override
    public Cell createCell(int x, int y, Object... params) {
        int lockId = params.length > 0 ? (Integer) params[0] : 0;
        return new LockCell(x, y, lockId);
    }
}