package model.factory.cellsfactory;

import model.elements.cells.LockCell;
import model.elements.cells.Cell;
import model.factory.CreationContext;

public class LockCellFactory extends CellFactory {
    private static final String TYPE = "lock";
    private static final int DEFAULT_LOCK_ID = 0;

    @Override
    protected Cell createCell(CreationContext context) {
        int lockId = context.getParameter("lockId", DEFAULT_LOCK_ID);
        return new LockCell(context.getX(), context.getY(), lockId);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}