package model.elements.cells;

public class WallCell extends Cell {
    public WallCell(int x, int y) {
        super(x, y, true);
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
    public String getImageName() { return "wall.png"; }
    @Override
    public Cell clone() { return new WallCell(x, y); }
}
