package model.factory.cellsfactory;

import model.elements.cells.TargetCell;
import model.elements.cells.Cell;
import model.factory.CreationContext;

public class TargetCellFactory extends CellFactory {
    private static final String TYPE = "target";

    @Override
    protected Cell createCell(CreationContext context) {
        return new TargetCell(context.getX(), context.getY());
    }

    @Override
    public String getType() {
        return TYPE;
    }
}