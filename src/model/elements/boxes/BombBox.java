package model.elements.boxes;

import model.elements.interfaces.Explosive;
import model.elements.boxes.states.*;

public class BombBox extends Box implements Explosive {
    private BombState state;

    public BombBox(int x, int y) {
        super(x, y);
        this.state = new InactiveBombState();
    }

    private BombBox(int x, int y, BombState state) {
        super(x, y);
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
        return new BombBox(x, y, state.clone());
    }
}
