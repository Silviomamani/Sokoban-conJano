package model.factory.boxesfactory;

import model.elements.boxes.NormalBox;
import model.elements.boxes.Box;
import model.factory.CreationContext;

public class NormalBoxFactory extends BoxFactory {
    private static final String TYPE = "normal";

    @Override
    protected Box createBox(CreationContext context) {
        return new NormalBox(context.getX(), context.getY());
    }

    @Override
    public String getType() {
        return TYPE;
    }
}