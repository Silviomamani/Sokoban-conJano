package manager;

import model.GameBoard;
import model.elements.Player;
import model.factory.boxesfactory.BoxFactory;
import model.factory.cellsfactory.CellFactory;
import java.util.function.BiConsumer;
import java.io.*;
import java.util.*;

// BUILDER: Construye niveles desde archivos .txt
public class LevelBuilder {
    private static final int DEFAULT_KEY_ID = 0;

    public static GameBoard buildLevel(String filepath) {
        try {
            List<String> lines = readFile(filepath);
            return parseLevel(lines);
        } catch (IOException e) {
            System.err.println("Error loading level: " + filepath);
            return createDefaultLevel();
        }
    }

    private static List<String> readFile(String filepath) throws IOException {
        List<String> lines = new ArrayList<>();
        InputStream is = LevelBuilder.class.getClassLoader().getResourceAsStream(filepath);

        if (is == null) {
            // Fallback al sistema de archivos (proyecto en ejecución local)
            java.nio.file.Path fsPath = java.nio.file.Paths.get(System.getProperty("user.dir"), "resources", filepath);
            if (!java.nio.file.Files.exists(fsPath)) {
                // Intento alternativo: si filepath ya incluye levels/..., anteponer solo resources/
                fsPath = java.nio.file.Paths.get(System.getProperty("user.dir"), "resources", filepath);
            }
            if (java.nio.file.Files.exists(fsPath)) {
                BufferedReader reader = java.nio.file.Files.newBufferedReader(fsPath);
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                reader.close();
                return lines;
            } else {
                // No se encontró: usar nivel por defecto
                return createDefaultLevelData();
            }
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    private static GameBoard parseLevel(List<String> lines) {
        if (lines.isEmpty()) return createDefaultLevel();

        int height = lines.size();
        int width = lines.stream().mapToInt(String::length).max().orElse(0);

        GameBoard board = new GameBoard(width, height);

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                parseCharacter(c, x, y, board);
            }
        }

        return board;
    }

    private static void parseCharacter(char c, int x, int y, GameBoard board) {
        BiConsumer<int[], GameBoard> action = Registry.MAP.getOrDefault(c, Registry.MAP.get(' '));
        action.accept(new int[]{x, y}, board);
    }

    // Registro de parsers por carácter (OCP)
    private static class Registry {
        private static final java.util.Map<Character, BiConsumer<int[], GameBoard>> MAP = new java.util.HashMap<>();
        static {
            MAP.put(' ', (pos, b) -> {
                CellFactory f = CellFactory.getFactory("empty");
                b.setCell(pos[0], pos[1], f.createCell(pos[0], pos[1]));
            });
            MAP.put('#', (pos, b) -> {
                CellFactory f = CellFactory.getFactory("wall");
                b.setCell(pos[0], pos[1], f.createCell(pos[0], pos[1]));
            });
            MAP.put('.', (pos, b) -> {
                CellFactory f = CellFactory.getFactory("target");
                b.setCell(pos[0], pos[1], f.createCell(pos[0], pos[1]));
            });
            MAP.put('C', (pos, b) -> {
                CellFactory f = CellFactory.getFactory("checkpoint");
                b.setCell(pos[0], pos[1], f.createCell(pos[0], pos[1]));
            });
            MAP.put('I', (pos, b) -> {
                CellFactory f = CellFactory.getFactory("slippery");
                b.setCell(pos[0], pos[1], f.createCell(pos[0], pos[1]));
            });
            MAP.put('L', (pos, b) -> {
                CellFactory f = CellFactory.getFactory("lock");
                b.setCell(pos[0], pos[1], f.createCell(pos[0], pos[1], DEFAULT_KEY_ID));
            });
            // Nueva celda destino para llave (ID 0)
            MAP.put('Y', (pos, b) -> {
                CellFactory f = CellFactory.getFactory("keytarget");
                b.setCell(pos[0], pos[1], f.createCell(pos[0], pos[1], DEFAULT_KEY_ID));
            });
            MAP.put('B', (pos, b) -> {
                BoxFactory f = BoxFactory.getFactory("normal");
                b.addBox(f.createBox(pos[0], pos[1]));
            });
            MAP.put('X', (pos, b) -> {
                BoxFactory f = BoxFactory.getFactory("bomb");
                b.addBox(f.createBox(pos[0], pos[1]));
            });
            MAP.put('K', (pos, b) -> {
                BoxFactory f = BoxFactory.getFactory("key");
                b.addBox(f.createBox(pos[0], pos[1], DEFAULT_KEY_ID));
            });
            MAP.put('P', (pos, b) -> {
                b.setPlayer(new Player(pos[0], pos[1]));
            });
        }
    }

    private static List<String> createDefaultLevelData() {
        return Arrays.asList(
                "##########",
                "#P   B  .#",
                "#        #",
                "#   X   .#",
                "##########"
        );
    }

    private static GameBoard createDefaultLevel() {
        return parseLevel(createDefaultLevelData());
    }
}