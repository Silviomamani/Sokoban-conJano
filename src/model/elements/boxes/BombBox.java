package model.elements.boxes;

public class BombBox extends Box {
    private int countdown;
    private static final int INITIAL_COUNTDOWN = 8;
    private boolean exploded;
    private boolean activated; // Nueva bandera

    public BombBox(int x, int y) {
        super(x, y);
        this.countdown = INITIAL_COUNTDOWN;
        this.exploded = false;
        this.activated = false; // Inicialmente no activada
    }

    @Override
    public void onPushed() {
        // Activar la bomba la primera vez que se empuja
        if (!activated) {
            activated = true;
        }

        // Decrementar solo cuando esta caja específica es empujada
        if (activated && countdown > 0) {
            countdown--;
            if (countdown == 0) {
                exploded = true;
            }
        }
    }

    public int getCountdown() {
        return countdown;
    }

    @Override
    public boolean isExploded() {
        return exploded;
    }

    @Override
    public String getImageName() {
        if (!activated) {
            return "box_bomb.png"; // Bomba sin activar
        } else if (exploded) {
            return "box_bomb.png"; // O una imagen de explosión si tienes
        } else if (countdown <= 2) {
            return "box_bomb_red.png";
        } else if (countdown <= 4) {
            return "box_bomb_orange.png";
        }
        return "box_bomb.png";
    }

    @Override
    public Box clone() {
        BombBox cloned = new BombBox(x, y);
        cloned.countdown = this.countdown;
        cloned.exploded = this.exploded;
        cloned.activated = this.activated;
        return cloned;
    }
}