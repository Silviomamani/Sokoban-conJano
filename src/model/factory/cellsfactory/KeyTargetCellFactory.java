package model.factory.cellsfactory;

import model.elements.cells.Cell;
import model.elements.cells.KeyTargetCell;

public class KeyTargetCellFactory extends CellFactory {
    @Override
    public Cell createCell(int x, int y, Object... params) {
        int lockId = 0;
        if (params != null && params.length > 0 && params[0] instanceof Integer) {
            lockId = (Integer) params[0];
        }
        return new KeyTargetCell(x, y, lockId);
    }
}


