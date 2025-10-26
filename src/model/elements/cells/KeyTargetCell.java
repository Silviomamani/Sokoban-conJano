package model.elements.cells;

import model.elements.cells.interfaces.BoxInteractive;
import model.elements.cells.interfaces.Targetable;
import model.elements.boxes.Box;
import model.elements.boxes.KeyBox;

/**
 * Celda objetivo específica para llaves
 * Combina múltiples capacidades
 */
public class KeyTargetCell extends Cell implements Targetable, BoxInteractive {
    private final int lockId;

    public KeyTargetCell(int x, int y, int lockId) {
        super(x, y, false);
        this.lockId = lockId;
    }

    public int getLockId() {
        return lockId;
    }

    @Override
    public boolean isTargetFor(Box box) {
        if (!(box instanceof KeyBox)) {
            return false;
        }
        KeyBox keyBox = (KeyBox) box;
        return keyBox.getKeyId() == this.lockId;
    }

    @Override
    public void onBoxEntered(Box box) {
        if (box instanceof KeyBox) {
            KeyBox keyBox = (KeyBox) box;
            if (keyBox.getKeyId() == this.lockId) {
                keyBox.notifyOnLock();
            }
        }
    }

    @Override
    public String getImageName() {
        return "target_key.png";
    }

    @Override
    public Cell clone() {
        return new KeyTargetCell(x, y, lockId);
    }
}
