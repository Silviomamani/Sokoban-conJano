package model.factory.cellsfactory;

import model.elements.cells.CheckpointCell;
import model.elements.cells.Cell;
import model.factory.CreationContext;

public class CheckpointCellFactory extends CellFactory {
    private static final String TYPE = "checkpoint";

    @Override
    protected Cell createCell(CreationContext context) {
        return new CheckpointCell(context.getX(), context.getY());
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
