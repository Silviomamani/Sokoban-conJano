package manager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

/**
 * Responsable de cargar recursos (imágenes, etc.)
 * Principios aplicados:
 * - Expert: Es experto en localización de recursos
 * - SRP: Solo maneja carga de recursos
 * - Low Coupling: Centraliza acceso a recursos
 */
public class ResourceManager {
    private static ResourceManager instance;
    private static final String RESOURCES_PATH = "resources/";

    private ResourceManager() {}

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    /**
     * Carga una imagen desde classpath o filesystem
     */
    public ImageIcon loadImage(String relativePath) {
        // Intenta desde classpath primero
        URL imgURL = getClass().getClassLoader().getResource(relativePath);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        }

        // Fallback a filesystem
        File fallback = new File(RESOURCES_PATH + relativePath);
        if (fallback.exists()) {
            return new ImageIcon(fallback.getAbsolutePath());
        }

        return null;
    }

    /**
     * Carga y escala una imagen automáticamente
     */
    public ImageIcon loadScaledImage(String relativePath, int maxWidth, int maxHeight) {
        ImageIcon icon = loadImage(relativePath);
        if (icon == null) return null;

        Image image = icon.getImage();
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        if (width <= 0 || height <= 0) return icon;

        double scale = Math.min((double) maxWidth / width, (double) maxHeight / height);

        if (scale < 1.0) {
            int scaledWidth = (int) (width * scale);
            int scaledHeight = (int) (height * scale);
            Image scaled = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }

        return icon;
    }

    /**
     * Obtiene ruta de sonido
     */
    public String getSoundPath(String soundName) {
        return "sounds/" + soundName;
    }
}