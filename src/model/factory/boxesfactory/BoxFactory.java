package model.factory.boxesfactory;

import model.elements.boxes.*;

// Factory Method para crear cajas
public abstract class BoxFactory {
    public abstract Box createBox(int x, int y, Object... params);

    public static BoxFactory getFactory(String type) {
        String key = type.toLowerCase();
        java.util.Map<String, BoxFactory> registry = Holder.REGISTRY;
        return registry.getOrDefault(key, new NormalBoxFactory());
    }

    private static class Holder {
        private static final java.util.Map<String, BoxFactory> REGISTRY = new java.util.HashMap<>();
        static {
            REGISTRY.put("normal", new NormalBoxFactory());
            REGISTRY.put("bomb", new BombBoxFactory());
            REGISTRY.put("key", new KeyBoxFactory());
        }
    }
}




