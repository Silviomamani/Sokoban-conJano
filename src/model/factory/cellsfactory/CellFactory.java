package model.factory.cellsfactory;

import model.elements.cells.Cell;
import model.factory.GameElementFactory;
import model.factory.CreationContext;

public abstract class CellFactory implements GameElementFactory<Cell> {
    @Override
    public final Cell create(CreationContext context) {
        validateContext(context);
        return createCell(context);
    }

    protected abstract Cell createCell(CreationContext context);

    protected void validateContext(CreationContext context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
    }
}