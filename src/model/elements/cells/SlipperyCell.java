package model.elements.cells;

public class SlipperyCell extends Cell {
    public SlipperyCell(int x, int y) {
        super(x, y, false);
    }

    @Override
    public boolean isTarget() { return false; }
    @Override
    public boolean isCheckpoint() { return false; }
    @Override
    public boolean isSlippery() { return true; }
    @Override
    public boolean isLock() { return false; }
    @Override
    public String getImageName() { return "slippery.png"; }
    @Override
    public Cell clone() { return new SlipperyCell(x, y); }
}