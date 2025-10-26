package model.elements.cells;

import model.elements.cells.interfaces.Lockable;

public class LockCell extends Cell implements Lockable {
    private final int lockId;

    public LockCell(int x, int y, int lockId) {
        super(x, y, true);
        this.lockId = lockId;
    }

    @Override
    public int getLockId() {
        return lockId;
    }

    @Override
    public String getImageName() {
        return "lock.png";
    }

    @Override
    public Cell clone() {
        return new LockCell(x, y, lockId);
    }
}