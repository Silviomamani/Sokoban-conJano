package model.factory.cellsfactory;

import model.elements.cells.*;

// Factory Method para crear celdas
public abstract class CellFactory {
    public abstract Cell createCell(int x, int y, Object... params);

    public static CellFactory getFactory(String type) {
        String key = type.toLowerCase();
        java.util.Map<String, CellFactory> registry = Holder.REGISTRY;
        return registry.getOrDefault(key, new EmptyCellFactory());
    }

    // Registro estático para cumplir OCP (extensión sin modificar switch)
    private static class Holder {
        private static final java.util.Map<String, CellFactory> REGISTRY = new java.util.HashMap<>();
        static {
            REGISTRY.put("empty", new EmptyCellFactory());
            REGISTRY.put("wall", new WallCellFactory());
            REGISTRY.put("target", new TargetCellFactory());
            REGISTRY.put("checkpoint", new CheckpointCellFactory());
            REGISTRY.put("slippery", new SlipperyCellFactory());
            REGISTRY.put("lock", new LockCellFactory());
            REGISTRY.put("keytarget", new KeyTargetCellFactory());
        }
    }
}





