package model.elements.cells;

public class EmptyCell extends Cell {
    public EmptyCell(int x, int y) {
        super(x, y, false);
    }

    @Override
    public boolean isTarget() { return false; }
    @Override
    public boolean isCheckpoint() { return false; }
    @Override
    public boolean isSlippery() { return false; }
    @Override
    public boolean isLock() { return false; }
    @Override
    public String getImageName() { return "empty.png"; }
    @Override
    public Cell clone() { return new EmptyCell(x, y); }
}