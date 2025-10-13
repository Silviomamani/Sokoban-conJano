package model.elements.cells;

import model.elements.boxes.KeyBox;

public class KeyTargetCell extends Cell {
    private int lockId;

    public KeyTargetCell(int x, int y, int lockId) {
        super(x, y, false);
        this.lockId = lockId;
    }

    public int getLockId() { return lockId; }

    @Override
    public boolean isTarget() { return false; }

    @Override
    public boolean isCheckpoint() { return false; }

    @Override
    public boolean isSlippery() { return false; }

    @Override
    public boolean isLock() { return false; }

    @Override
    public String getImageName() { return "target.png"; }

    @Override
    public Cell clone() { return new KeyTargetCell(x, y, lockId); }

    @Override
    public void onBoxEntered(model.elements.boxes.Box box) {
        if (box instanceof KeyBox) {
            KeyBox keyBox = (KeyBox) box;
            if (keyBox.getKeyId() == this.lockId) {
                keyBox.notifyOnLock();
            }
        }
    }
}
