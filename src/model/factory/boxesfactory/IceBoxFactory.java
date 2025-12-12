package model.factory.boxesfactory;

import model.elements.boxes.IceBox;
import model.elements.boxes.Box;
import model.factory.CreationContext;

// Factory para crear IceBox

public class IceBoxFactory extends BoxFactory {
    private static final String TYPE = "ice";

    @Override
    protected Box createBox(CreationContext context) {
        return new IceBox(context.getX(), context.getY());
    }

    @Override
    public String getType() {
        return TYPE;
    }
}

