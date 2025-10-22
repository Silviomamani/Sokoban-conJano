package model.elements.boxes;

import model.observer.Observable;
import model.observer.Observer;
import model.elements.boxes.interfaces.Identifiable;
import java.util.ArrayList;
import java.util.List;

public class KeyBox extends Box implements Observable, Identifiable {
    private static final String IMAGE_NAME = "box_key.png";
    private final int keyId;
    private final List<Observer> observers;

    public KeyBox(int x, int y, int keyId) {
        super(x, y);
        this.keyId = keyId;
        this.observers = new ArrayList<>();
    }

    @Override
    public int getId() {
        return keyId;
    }

    // Deprecated: Use getId() instead
    @Deprecated
    public int getKeyId() {
        return getId();
    }

    @Override
    public void onPushed() {
        // No action on push
    }

    public void notifyOnLock() {
        notifyObservers();
    }

    @Override
    public boolean countsForVictory() {
        return false; // Keys don't count for victory
    }

    @Override
    public String getImageName() {
        return IMAGE_NAME;
    }

    @Override
    public Box clone() {
        KeyBox cloned = new KeyBox(x, y, keyId);
        cloned.observers.addAll(this.observers);
        return cloned;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}