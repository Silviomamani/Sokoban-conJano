package model.factory.boxesfactory;

import model.elements.boxes.*;

// Factory Method para crear cajas
public abstract class BoxFactory {
    public abstract Box createBox(int x, int y, Object... params);

    public static BoxFactory getFactory(String type) {
        switch (type.toLowerCase()) {
            case "normal":
                return new NormalBoxFactory();
            case "bomb":
                return new BombBoxFactory();
            case "key":
                return new KeyBoxFactory();
            default:
                return new NormalBoxFactory();
        }
    }
}




