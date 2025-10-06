package model.factory.boxesfactory;

import model.elements.boxes.Box;
import model.elements.boxes.KeyBox;

public class KeyBoxFactory extends BoxFactory {
    @Override
    public Box createBox(int x, int y, Object... params) {
        int keyId = params.length > 0 ? (Integer) params[0] : 0;
        return new KeyBox(x, y, keyId);
    }
}