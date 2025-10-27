package model.factory.boxesfactory;

import model.elements.boxes.KeyBox;
import model.elements.boxes.Box;
import model.factory.CreationContext;

public class KeyBoxFactory extends BoxFactory {
    private static final String TYPE = "key";
    private static final int DEFAULT_KEY_ID = 0;

    @Override
    protected Box createBox(CreationContext context) {
        int keyId = context.getParameter("keyId", DEFAULT_KEY_ID);
        return new KeyBox(context.getX(), context.getY(), keyId);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}