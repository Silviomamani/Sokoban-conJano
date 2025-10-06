package model.elements;

public class Player extends BoardElement {
    public Player(int x, int y) {
        super(x, y, true);
    }

    @Override
    public String getImageName() {
        return "player.png";
    }

    @Override
    public Player clone() {
        return new Player(x, y);
    }
}
