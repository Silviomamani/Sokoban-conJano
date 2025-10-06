package model.elements.cells;

public class CheckpointCell extends Cell {
    public CheckpointCell(int x, int y) {
        super(x, y, false);
    }

    @Override
    public boolean isTarget() { return false; }
    @Override
    public boolean isCheckpoint() { return true; }
    @Override
    public boolean isSlippery() { return false; }
    @Override
    public boolean isLock() { return false; }
    @Override
    public String getImageName() { return "checkpoint.png"; }
    @Override
    public Cell clone() { return new CheckpointCell(x, y); }
}