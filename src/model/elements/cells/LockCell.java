package model.elements.cells;

public class LockCell extends Cell {
    private int lockId;

    public LockCell(int x, int y, int lockId) {
        super(x, y, true);
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
    public boolean isLock() { return true; }
    @Override
    public String getImageName() { return "lock.png"; }
    @Override
    public Cell clone() { return new LockCell(x, y, lockId); }
}
