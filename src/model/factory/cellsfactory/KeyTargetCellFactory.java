package model.factory.cellsfactory;

import model.elements.cells.KeyTargetCell;
import model.elements.cells.Cell;
import model.factory.CreationContext;

public class KeyTargetCellFactory extends CellFactory {
    private static final String TYPE = "keytarget";
    private static final int DEFAULT_KEY_ID = 0;

    @Override
    protected Cell createCell(CreationContext context) {
        int keyId = context.getParameter("keyId", DEFAULT_KEY_ID);
        return new KeyTargetCell(context.getX(), context.getY(), keyId);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
