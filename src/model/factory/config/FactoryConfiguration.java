package model.factory.config;

import model.factory.GameElementRegistry;
import model.factory.boxesfactory.*;
import model.factory.cellsfactory.*;
import model.elements.boxes.Box;
import model.elements.cells.Cell;

public class FactoryConfiguration {
    private static GameElementRegistry<Box> boxRegistry;
    private static GameElementRegistry<Cell> cellRegistry;
    private static boolean initialized = false;

    public static synchronized void initialize() {
        if (initialized) {
            return;
        }

        // Inicializar registry de cajas
        boxRegistry = new GameElementRegistry<>(new NormalBoxFactory());
        boxRegistry.register(new NormalBoxFactory());
        boxRegistry.register(new BombBoxFactory());
        boxRegistry.register(new KeyBoxFactory());

        // Inicializar registry de celdas
        cellRegistry = new GameElementRegistry<>(new EmptyCellFactory());
        cellRegistry.register(new EmptyCellFactory());
        cellRegistry.register(new WallCellFactory());
        cellRegistry.register(new TargetCellFactory());
        cellRegistry.register(new CheckpointCellFactory());
        cellRegistry.register(new SlipperyCellFactory());
        cellRegistry.register(new LockCellFactory());
        cellRegistry.register(new KeyTargetCellFactory());

        initialized = true;
    }

    public static GameElementRegistry<Box> getBoxRegistry() {
        if (!initialized) {
            initialize();
        }
        return boxRegistry;
    }

    public static GameElementRegistry<Cell> getCellRegistry() {
        if (!initialized) {
            initialize();
        }
        return cellRegistry;
    }

    /**
     * Permite agregar factories personalizadas en runtime
     * Open/Closed Principle en acción
     */
    public static void registerBoxFactory(BoxFactory factory) {
        getBoxRegistry().register(factory);
    }

    public static void registerCellFactory(CellFactory factory) {
        getCellRegistry().register(factory);
    }
}