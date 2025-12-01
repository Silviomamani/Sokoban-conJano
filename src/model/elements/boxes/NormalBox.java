package model.elements.boxes;

public class NormalBox extends Box {
    private static final String IMAGE_NAME = "box_normal.png";

    public NormalBox(int x, int y) {
        super(x, y);
    }

    @Override
    public void onPushed() {
    }

    @Override
    public String getImageName() {
        return IMAGE_NAME;
    }

    @Override
    public Box clone() {
        return new NormalBox(x, y);
    }
}