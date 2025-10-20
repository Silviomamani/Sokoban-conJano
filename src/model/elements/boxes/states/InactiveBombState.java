package model.elements.boxes.states;

public class InactiveBombState implements BombState {
    private static final String INACTIVE_IMAGE = "box_bomb.png";
    private static final int INITIAL_COUNTDOWN = 8;

    @Override
    public BombState onPush() {
        return new ActiveBombState(INITIAL_COUNTDOWN);
    }

    @Override
    public String getImageName() {
        return INACTIVE_IMAGE;
    }

    @Override
    public int getCountdown() {
        return INITIAL_COUNTDOWN;
    }

    @Override
    public boolean isExploded() {
        return false;
    }

    @Override
    public BombState clone() {
        return new InactiveBombState();
    }
}