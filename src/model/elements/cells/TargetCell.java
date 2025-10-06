package model.elements.cells;

public class TargetCell extends Cell {
    public TargetCell(int x, int y) {
        super(x, y, false);
    }

    @Override
    public boolean isTarget() { return true; }
    @Override
    public boolean isCheckpoint() { return false; }
    @Override
    public boolean isSlippery() { return false; }
    @Override
    public boolean isLock() { return false; }
    @Override
    public String getImageName() { return "target.png"; }
    @Override
    public Cell clone() { return new TargetCell(x, y); }
}