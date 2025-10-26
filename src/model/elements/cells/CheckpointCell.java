package model.elements.cells;

import model.elements.cells.interfaces.Checkpointable;

public class CheckpointCell extends Cell implements Checkpointable {

    public CheckpointCell(int x, int y) {
        super(x, y, false);
    }

    @Override
    public String getImageName() {
        return "checkpoint.png";
    }

    @Override
    public Cell clone() {
        return new CheckpointCell(x, y);
    }
}