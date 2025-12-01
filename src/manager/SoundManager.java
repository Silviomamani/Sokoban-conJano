package manager;

import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * SINGLETON: Gestor de sonidos del juego

 */
public class SoundManager {
    private static SoundManager instance;
    private Map<String, Clip> sounds;
    private boolean soundEnabled;

    private SoundManager() {
        sounds = new HashMap<>();
        soundEnabled = true;
        initializeSounds();
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    private void initializeSounds() {
        ResourceManager resources = ResourceManager.getInstance();

        loadSound("move", resources.getSoundPath("move.wav"));
        loadSound("victory", resources.getSoundPath("victory.wav"));
        loadSound("explosion", resources.getSoundPath("explosion.wav"));
        loadSound("checkpoint", resources.getSoundPath("checkpoint.wav"));

        System.out.println("Sonidos inicializados: " + sounds.size() + " cargados");
    }

    public void loadSound(String name, String filepath) {
        try {

            java.net.URL soundURL = getClass().getClassLoader().getResource(filepath);

            if (soundURL != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                sounds.put(name, clip);
                System.out.println("✓ Sonido cargado: " + name);
            } else {

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

    public void dispose() {
        for (Clip clip : sounds.values()) {
            if (clip != null) {
                clip.close();
            }
        }
        sounds.clear();
    }
}