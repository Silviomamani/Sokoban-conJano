package view;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import manager.GameManager;
import model.GameBoard;
import model.elements.Player;
import model.elements.boxes.Box;
import model.elements.cells.Cell;

public class GamePanel extends JPanel {
    private static final int CELL_SIZE = 50;
    private Map<String, Image> imageCache;

    public GamePanel() {
        imageCache = new HashMap<>();
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        loadImages();
    }

    private void loadImages() {
        // Cargar imágenes de recursos
        String[] imageNames = {
                "empty.png", "wall.png", "target.png", "target_key.png","checkpoint.png",
                "slippery.png", "lock.png", "box_normal.png", "box_bomb.png",
                "box_bomb_orange.png", "box_bomb_red.png", "box_key.png", "box_ice.png", "player.png"
        };

        for (String name : imageNames) {
            loadImage(name);
        }
    }

    private void loadImage(String name) {
        try {
            // Intentar cargar desde resources/images/ directamente
            String path = "images/" + name;
            java.net.URL imgURL = getClass().getClassLoader().getResource(path);

            if (imgURL != null) {
                Image img = new ImageIcon(imgURL).getImage();
                imageCache.put(name, img);
                System.out.println("✓ Imagen cargada: " + name);
            } else {
                // Intentar con ruta absoluta como fallback
                File imgFile = new File("resources/images/" + name);
                if (imgFile.exists()) {
                    Image img = new ImageIcon(imgFile.getAbsolutePath()).getImage();
                    imageCache.put(name, img);
                    System.out.println("✓ Imagen cargada desde archivo: " + name);
                } else {
                    System.out.println("✗ No se encontró: " + name);

                }
            }
        } catch (Exception e) {
            System.out.println("✗ Error al cargar " + name + ": " + e.getMessage());

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        GameBoard board = GameManager.getInstance().getCurrentBoard();
        if (board == null) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Centrar el tablero
        int offsetX = (getWidth() - board.getWidth() * CELL_SIZE) / 2;
        int offsetY = (getHeight() - board.getHeight() * CELL_SIZE) / 2;

        // Dibujar celdas
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Cell cell = board.getCell(x, y);
                if (cell != null) {
                    drawElement(g2d, cell, offsetX, offsetY);
                }
            }
        }

        // Dibujar cajas
        for (Box box : board.getBoxes()) {
            drawElement(g2d, box, offsetX, offsetY);
        }

        // Dibujar jugador
        Player player = board.getPlayer();
        if (player != null) {
            drawElement(g2d, player, offsetX, offsetY);
        }
    }

    private void drawElement(Graphics2D g, model.elements.BoardElement element, int offsetX, int offsetY) {
        int x = offsetX + element.getX() * CELL_SIZE;
        int y = offsetY + element.getY() * CELL_SIZE;

        Image img = imageCache.get(element.getImageName());
        if (img != null) {
            g.drawImage(img, x, y, CELL_SIZE, CELL_SIZE, null);
        }
    }
}