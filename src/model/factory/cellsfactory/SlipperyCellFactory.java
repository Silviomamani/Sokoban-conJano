package model.factory.cellsfactory;

import model.elements.cells.SlipperyCell;
import model.elements.cells.Cell;
import model.factory.CreationContext;

public class SlipperyCellFactory extends CellFactory {
    private static final String TYPE = "slippery";

    @Override
    protected Cell createCell(CreationContext context) {
        return new SlipperyCell(context.getX(), context.getY());
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
