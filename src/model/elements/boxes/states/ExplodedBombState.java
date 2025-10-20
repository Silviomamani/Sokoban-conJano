package model.elements.boxes.states;

public class ExplodedBombState implements BombState {
    private static final String EXPLODED_IMAGE = "box_bomb_exploded.png";

    @Override
    public BombState onPush() {
        return this; // No cambia más
    }

    @Override
    public String getImageName() {
        return EXPLODED_IMAGE;
    }

    @Override
    public int getCountdown() {
        return 0;
    }

    @Override
    public boolean isExploded() {
        return true;
    }

    @Override
    public BombState clone() {
        return new ExplodedBombState();
    }
}