package model.elements.boxes.states;

public class ActiveBombState implements BombState {
    private static final String DEFAULT_IMAGE = "box_bomb.png";
    private static final String ORANGE_IMAGE = "box_bomb_orange.png";
    private static final String RED_IMAGE = "box_bomb_red.png";

    private final int countdown;
    private final int initialCountdown;

    public ActiveBombState(int countdown) {
        this(countdown, countdown);
    }

    public ActiveBombState(int countdown, int initialCountdown) {
        this.countdown = countdown;
        this.initialCountdown = initialCountdown;
    }

    private int calculateOrangeThreshold() {
        int threshold = initialCountdown / 2;
        return threshold < 1 ? 1 : threshold;
    }

    private int calculateRedThreshold() {
        int orangeThreshold = calculateOrangeThreshold();
        int threshold = orangeThreshold / 2;
        return threshold < 1 ? 1 : threshold;
    }

    @Override
    public BombState onPush() {
        int newCountdown = countdown - 1;
        if (newCountdown <= 0) {
            return new ExplodedBombState();
        }
        return new ActiveBombState(newCountdown, initialCountdown);
    }

    @Override
    public String getImageName() {
        int redThreshold = calculateRedThreshold();
        int orangeThreshold = calculateOrangeThreshold();
        
        if (countdown <= redThreshold) {
            return RED_IMAGE;
        } else if (countdown <= orangeThreshold) {
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
        return new ActiveBombState(countdown, initialCountdown);
    }
}