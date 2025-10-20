package model.elements.boxes.states;

public class ActiveBombState implements BombState {
    private static final String DEFAULT_IMAGE = "box_bomb.png";
    private static final String ORANGE_IMAGE = "box_bomb_orange.png";
    private static final String RED_IMAGE = "box_bomb_red.png";
    private static final int ORANGE_THRESHOLD = 4;
    private static final int RED_THRESHOLD = 2;

    private final int countdown;

    public ActiveBombState(int countdown) {
        this.countdown = countdown;
    }

    @Override
    public BombState onPush() {
        int newCountdown = countdown - 1;
        if (newCountdown <= 0) {
            return new ExplodedBombState();
        }
        return new ActiveBombState(newCountdown);
    }

    @Override
    public String getImageName() {
        if (countdown <= RED_THRESHOLD) {
            return RED_IMAGE;
        } else if (countdown <= ORANGE_THRESHOLD) {
            return ORANGE_IMAGE;
        }
        return DEFAULT_IMAGE;
    }

    @Override
    public int getCountdown() {
        return countdown;
    }

    @Override
    public boolean isExploded() {
        return false;
    }

    @Override
    public BombState clone() {
        return new ActiveBombState(countdown);
    }
}