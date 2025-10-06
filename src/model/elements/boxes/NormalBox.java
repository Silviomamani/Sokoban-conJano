package model.elements.boxes;

public class NormalBox extends Box {
    public NormalBox(int x, int y) {
        super(x, y);
    }

    @Override
    public void onPushed() {
        // No hace nada especial
    }

    @Override
    public boolean isExploded() {
        return false;
    }

    @Override
    public String getImageName() {
        return "box_normal.png";
    }

    @Override
    public Box clone() {
        return new NormalBox(x, y);
    }
}