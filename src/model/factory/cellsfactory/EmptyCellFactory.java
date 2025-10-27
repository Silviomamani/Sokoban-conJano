package model.factory.cellsfactory;

import model.elements.cells.EmptyCell;
import model.elements.cells.Cell;
import model.factory.CreationContext;

public class EmptyCellFactory extends CellFactory {
    private static final String TYPE = "empty";

    @Override
    protected Cell createCell(CreationContext context) {
        return new EmptyCell(context.getX(), context.getY());
    }

    @Override
    public String getType() {
        return TYPE;
    }
}