package model.factory.boxesfactory;

import model.elements.boxes.Box;
import model.factory.GameElementFactory;
import model.factory.CreationContext;

public abstract class BoxFactory implements GameElementFactory<Box> {
    // Template Method para validación común
    @Override
    public final Box create(CreationContext context) {
        validateContext(context);
        return createBox(context);
    }

    protected abstract Box createBox(CreationContext context);

    protected void validateContext(CreationContext context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
    }
}