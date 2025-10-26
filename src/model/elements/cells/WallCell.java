package model.elements.cells;

public class WallCell extends Cell {
    public WallCell(int x, int y) {
        super(x, y, true);
    }

    @Override
    public String getImageName() {
        return "wall.png";
    }

    @Override
    public Cell clone() {
        return new WallCell(x, y);
    }
}