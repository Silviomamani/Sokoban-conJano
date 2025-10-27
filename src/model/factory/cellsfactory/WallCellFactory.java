package model.factory.cellsfactory;

import model.elements.cells.WallCell;
import model.elements.cells.Cell;
import model.factory.CreationContext;

public class WallCellFactory extends CellFactory {
    private static final String TYPE = "wall";

    @Override
    protected Cell createCell(CreationContext context) {
        return new WallCell(context.getX(), context.getY());
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
