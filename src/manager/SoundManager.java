package manager;

import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * SINGLETON: Gestor de sonidos del juego
 * Principios aplicados:
 * - SRP: Solo maneja carga y reproducción de sonidos
 * - Expert: Conoce qué sonidos necesita el juego
 * - Lazy Initialization: Carga sonidos al inicializar
 */
public class SoundManager {
    private static SoundManager instance;
    private Map<String, Clip> sounds;
    private boolean soundEnabled;

    private SoundManager() {
        sounds = new HashMap<>();
        soundEnabled = true;
        initializeSounds(); // Auto-inicialización
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Inicializa todos los sonidos del juego
     * Principio Expert: SoundManager sabe qué sonidos necesita el juego
     */
    private void initializeSounds() {
        ResourceManager resources = ResourceManager.getInstance();

        // Cargar todos los sonidos necesarios
        loadSound("move", resources.getSoundPath("move.wav"));
        loadSound("victory", resources.getSoundPath("victory.wav"));
        loadSound("explosion", resources.getSoundPath("explosion.wav"));
        loadSound("checkpoint", resources.getSoundPath("checkpoint.wav"));

        System.out.println("Sonidos inicializados: " + sounds.size() + " cargados");
    }

    public void loadSound(String name, String filepath) {
        try {
            // Intentar cargar desde resources/sounds/ usando ClassLoader
            java.net.URL soundURL = getClass().getClassLoader().getResource(filepath);

            if (soundURL != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                sounds.put(name, clip);
                System.out.println("✓ Sonido cargado: " + name);
            } else {
                // Fallback: intentar cargar desde archivo
                File soundFile = new File("resources/" + filepath);
                if (soundFile.exists()) {
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    sounds.put(name, clip);
                    System.out.println("✓ Sonido cargado desde archivo: " + name);
                } else {
                    System.err.println("✗ Error loading sound: " + filepath + " (no se encontró)");
                }
            }
        } catch (Exception e) {
            System.err.println("✗ Error loading sound: " + filepath + " - " + e.getMessage());
        }
    }

    public void playSound(String name) {
        if (!soundEnabled) return;

        Clip clip = sounds.get(name);
        if (clip != null) {
            // Detener y reiniciar el clip para permitir múltiples reproducciones
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        } else {
            System.err.println("Sonido no encontrado: " + name);
        }
    }

    public void toggleSound() {
        soundEnabled = !soundEnabled;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    /**
     * Libera recursos de todos los sonidos
     * Útil para cuando se cierra la aplicación
     */
    public void dispose() {
        for (Clip clip : sounds.values()) {
            if (clip != null) {
                clip.close();
            }
        }
        sounds.clear();
    }
}