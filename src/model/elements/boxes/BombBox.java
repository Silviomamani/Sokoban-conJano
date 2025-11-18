package model.elements.boxes;

import model.elements.boxes.interfaces.Explosive;
import model.elements.boxes.states.*;

public class BombBox extends Box implements Explosive {
    private BombState state;
    private final int maxPushes;

    public BombBox(int x, int y) {
        this(x, y, 8);
    }

    public BombBox(int x, int y, int maxPushes) {
        super(x, y);
        this.maxPushes = maxPushes;
        this.state = new InactiveBombState(maxPushes);
    }

    private BombBox(int x, int y, BombState state, int maxPushes) {
        super(x, y);
        this.maxPushes = maxPushes;
        this.state = state;
    }

    @Override
    public void onPushed() {
        state = state.onPush();
    }

    public int getCountdown() {
        return state.getCountdown();
    }

    @Override
    public boolean isExploded() {
        return state.isExploded();
    }

    @Override
    public String getImageName() {
        return state.getImageName();
    }

    @Override
    public Box clone() {
        return new BombBox(x, y, state.clone(), maxPushes);
    }
}
