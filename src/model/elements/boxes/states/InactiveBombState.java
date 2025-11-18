package model.elements.boxes.states;

public class InactiveBombState implements BombState {
    private static final String INACTIVE_IMAGE = "box_bomb.png";
    private static final int DEFAULT_INITIAL_COUNTDOWN = 8;
    
    private final int initialCountdown;

    public InactiveBombState() {
        this(DEFAULT_INITIAL_COUNTDOWN);
    }

    public InactiveBombState(int initialCountdown) {
        this.initialCountdown = initialCountdown;
    }

    @Override
    public BombState onPush() {
        return new ActiveBombState(initialCountdown);
    }

    @Override
    public String getImageName() {
        return INACTIVE_IMAGE;
    }

    @Override
    public int getCountdown() {
        return initialCountdown;
    }

    @Override
    public boolean isExploded() {
        return false;
    }

    @Override
    public BombState clone() {
        return new InactiveBombState(initialCountdown);
    }
}