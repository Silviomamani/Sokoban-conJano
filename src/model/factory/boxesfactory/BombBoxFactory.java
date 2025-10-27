package model.factory.boxesfactory;

import model.elements.boxes.BombBox;
import model.elements.boxes.Box;
import model.factory.CreationContext;

public class BombBoxFactory extends BoxFactory {
    private static final String TYPE = "bomb";

    @Override
    protected Box createBox(CreationContext context) {
        return new BombBox(context.getX(), context.getY());
    }

    @Override
    public String getType() {
        return TYPE;
    }
}