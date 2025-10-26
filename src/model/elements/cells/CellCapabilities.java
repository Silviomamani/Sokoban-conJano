package model.elements.cells;

import model.elements.cells.interfaces.*;

/**
 * Clase de utilidad para queries de tipo (Type-safe)
 * Aplica Expert Pattern - encapsula conocimiento sobre tipos
 */
public class CellCapabilities {

    public static boolean isTargetable(Cell cell) {
        return cell instanceof Targetable;
    }

    public static boolean isCheckpoint(Cell cell) {
        return cell instanceof Checkpointable;
    }

    public static boolean isSlippery(Cell cell) {
        return cell instanceof Slippery;
    }

    public static boolean isLockable(Cell cell) {
        return cell instanceof Lockable;
    }

    public static boolean isInteractive(Cell cell) {
        return cell instanceof BoxInteractive;
    }

    /**
     * Cast seguro para Targetable
     */
    public static Targetable asTargetable(Cell cell) {
        if (cell instanceof Targetable) {
            return (Targetable) cell;
        }
        return null;
    }

    /**
     * Cast seguro para Lockable
     */
    public static Lockable asLockable(Cell cell) {
        if (cell instanceof Lockable) {
            return (Lockable) cell;
        }
        return null;
    }
}