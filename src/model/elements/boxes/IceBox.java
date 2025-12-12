package model.elements.boxes;

import model.elements.boxes.interfaces.Slidable;

/**
 * Caja de hielo que se desliza 2 casillas al ser empujada
 * Principios aplicados:
 * - SRP: Responsabilidad única de representar una caja de hielo
 * - OCP: Extensible mediante Slidable
 */
public class IceBox extends Box implements Slidable {
    private static final String IMAGE_NAME = "box_ice.png";
    private static final int SLIDE_DISTANCE = 2;

    public IceBox(int x, int y) {
        super(x, y);
    }

    @Override
    public int getSlideDistance() {
        return SLIDE_DISTANCE;
    }

    @Override
    public void onPushed() {
        // No necesita lógica especial al ser empujada
    }

    @Override
    public String getImageName() {
        return IMAGE_NAME;
    }

    @Override
    public Box clone() {
        return new IceBox(x, y);
    }
}

