package model.factory.boxesfactory;

import model.elements.boxes.BombBox;
import model.elements.boxes.Box;
import model.factory.CreationContext;

public class BombBoxFactory extends BoxFactory {
    private static final String TYPE = "bomb";
    private static final int DEFAULT_PUSHES = 8;

    @Override
    protected Box createBox(CreationContext context) {
        int pushes = context.getParameter("pushes", DEFAULT_PUSHES);
        return new BombBox(context.getX(), context.getY(), pushes);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}