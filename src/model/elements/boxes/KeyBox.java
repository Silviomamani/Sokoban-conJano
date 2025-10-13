package model.elements.boxes;

import model.observer.Observable;
import model.observer.Observer;
import java.util.ArrayList;
import java.util.List;

public class KeyBox extends Box implements Observable {
    private int keyId;
    private List<Observer> observers;

    public KeyBox(int x, int y, int keyId) {
        super(x, y);
        this.keyId = keyId;
        this.observers = new ArrayList<>();
    }

    public int getKeyId() {
        return keyId;
    }

    @Override
    public void onPushed() {
        // No hace nada en el push
    }

    public void notifyOnLock() {
        notifyObservers();
    }

    @Override
    public boolean isExploded() {
        return false;
    }

    @Override
    public boolean countsForVictory() { return false; }

    @Override
    public String getImageName() {
        return "box_key.png";
    }

    @Override
    public Box clone() {
        KeyBox cloned = new KeyBox(x, y, keyId);
        cloned.observers = new ArrayList<>(this.observers);
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