package model.elements;

// Clase base abstracta para todos los elementos del tablero
public abstract class BoardElement {
    protected int x;
    protected int y;
    protected boolean solid; // Si bloquea el movimiento

    public BoardElement(int x, int y, boolean solid) {
        this.x = x;
        this.y = y;
        this.solid = solid;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean isSolid() { return solid; }

    public abstract String getImageName();
    public abstract BoardElement clone();
}