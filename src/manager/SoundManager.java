package manager;

import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

// Manager de sonidos
public class SoundManager {
    private static SoundManager instance;
    private Map<String, Clip> sounds;
    private boolean soundEnabled;

    private SoundManager() {
        sounds = new HashMap<>();
        soundEnabled = true;
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
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
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void toggleSound() {
        soundEnabled = !soundEnabled;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }
}