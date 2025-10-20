package model.elements.boxes.states;

public interface BombState {
    BombState onPush();
    String getImageName();
    int getCountdown();
    boolean isExploded();
    BombState clone();
}