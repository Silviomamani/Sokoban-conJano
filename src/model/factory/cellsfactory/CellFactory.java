package model.factory.cellsfactory;

import model.elements.cells.*;

// Factory Method para crear celdas
public abstract class CellFactory {
    public abstract Cell createCell(int x, int y, Object... params);

    public static CellFactory getFactory(String type) {
        switch (type.toLowerCase()) {
            case "empty":
                return new EmptyCellFactory();
            case "wall":
                return new WallCellFactory();
            case "target":
                return new TargetCellFactory();
            case "checkpoint":
                return new CheckpointCellFactory();
            case "slippery":
                return new SlipperyCellFactory();
            case "lock":
                return new LockCellFactory();
            default:
                return new EmptyCellFactory();
        }
    }
}





