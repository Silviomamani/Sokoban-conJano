package manager;

import model.GameBoard;
import model.elements.Player;
import model.elements.boxes.Box;
import model.elements.cells.Cell;
import model.factory.CreationContext;
import model.factory.config.FactoryConfiguration;
import java.io.*;
import java.util.*;

/**
 * Builder Pattern - Construye niveles desde archivos
 * High Cohesion: Responsabilidad única de parsear niveles
 */
public class LevelBuilder {
    private static final int DEFAULT_KEY_ID = 0;

    // Strategy Pattern para parseo de caracteres
    private interface CharacterParser {
        void parse(int x, int y, GameBoard board);
    }

    private static final Map<Character, CharacterParser> PARSERS = new HashMap<>();

    static {
        // Inicializar configuration
        FactoryConfiguration.initialize();

        // Registrar parsers - Open/Closed Principle
        PARSERS.put(' ', (x, y, b) -> {
            Cell cell = FactoryConfiguration.getCellRegistry()
                    .create("empty", CreationContext.builder(x, y).build());
            b.setCell(x, y, cell);
        });

        PARSERS.put('#', (x, y, b) -> {
            Cell cell = FactoryConfiguration.getCellRegistry()
                    .create("wall", CreationContext.builder(x, y).build());
            b.setCell(x, y, cell);
        });

        PARSERS.put('.', (x, y, b) -> {
            Cell cell = FactoryConfiguration.getCellRegistry()
                    .create("target", CreationContext.builder(x, y).build());
            b.setCell(x, y, cell);
        });

        PARSERS.put('C', (x, y, b) -> {
            Cell cell = FactoryConfiguration.getCellRegistry()
                    .create("checkpoint", CreationContext.builder(x, y).build());
            b.setCell(x, y, cell);
        });

        PARSERS.put('I', (x, y, b) -> {
            Cell cell = FactoryConfiguration.getCellRegistry()
                    .create("slippery", CreationContext.builder(x, y).build());
            b.setCell(x, y, cell);
        });

        PARSERS.put('L', (x, y, b) -> {
            Cell cell = FactoryConfiguration.getCellRegistry()
                    .create("lock", CreationContext.builder(x, y)
                            .withLockId(DEFAULT_KEY_ID).build());
            b.setCell(x, y, cell);
        });

        PARSERS.put('Y', (x, y, b) -> {
            Cell cell = FactoryConfiguration.getCellRegistry()
                    .create("keytarget", CreationContext.builder(x, y)
                            .withKeyId(DEFAULT_KEY_ID).build());
            b.setCell(x, y, cell);
        });

        PARSERS.put('B', (x, y, b) -> {
            Box box = FactoryConfiguration.getBoxRegistry()
                    .create("normal", CreationContext.builder(x, y).build());
            b.addBox(box);
        });

        PARSERS.put('X', (x, y, b) -> {
            Box box = FactoryConfiguration.getBoxRegistry()
                    .create("bomb", CreationContext.builder(x, y).build());
            b.addBox(box);
        });

        PARSERS.put('K', (x, y, b) -> {
            Box box = FactoryConfiguration.getBoxRegistry()
                    .create("key", CreationContext.builder(x, y)
                            .withKeyId(DEFAULT_KEY_ID).build());
            b.addBox(box);
        });

        PARSERS.put('P', (x, y, b) -> {
            b.setPlayer(new Player(x, y));
        });
    }

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
            java.nio.file.Path fsPath = java.nio.file.Paths.get(
                    System.getProperty("user.dir"), "resources", filepath);
            if (java.nio.file.Files.exists(fsPath)) {
                BufferedReader reader = java.nio.file.Files.newBufferedReader(fsPath);
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                reader.close();
                return lines;
            } else {
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
            int x = 0;
            while (x < line.length()) {
                char c = line.charAt(x);
                int skipChars = parseCharacter(c, x, y, board, line);
                x += skipChars > 0 ? skipChars : 1; // Saltar caracteres del patrón o avanzar 1
            }
        }

        return board;
    }

    private static int parseCharacter(char c, int x, int y, GameBoard board, String line) {
        // Detectar patrón x(8) para bombas
        if (c == 'X' || c == 'x') {
            int[] result = extractPushesFromLine(line, x);
            int pushes = result[0];
            int skipChars = result[1];
            
            Box box = FactoryConfiguration.getBoxRegistry()
                    .create("bomb", CreationContext.builder(x, y)
                            .withParameter("pushes", pushes).build());
            board.addBox(box);
            return skipChars;
        }
        
        CharacterParser parser = PARSERS.getOrDefault(c, PARSERS.get(' '));
        parser.parse(x, y, board);
        return 0;
    }

    private static int[] extractPushesFromLine(String line, int x) {
        // Buscar patrón (número) después de x
        if (x + 1 < line.length() && line.charAt(x + 1) == '(') {
            int start = x + 2;
            int end = start;
            while (end < line.length() && Character.isDigit(line.charAt(end))) {
                end++;
            }
            if (end < line.length() && line.charAt(end) == ')') {
                try {
                    int pushes = Integer.parseInt(line.substring(start, end));
                    // Retornar [pushes, caracteres a saltar]
                    // Saltar desde x hasta después de ')' = end - x + 1
                    return new int[]{pushes, end - x + 1};
                } catch (NumberFormatException e) {
                    // Si no se puede parsear, usar valor por defecto
                }
            }
        }
        // Valor por defecto si no se encuentra el patrón
        return new int[]{8, 0};
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

    /**
     * Permite registrar parsers personalizados en runtime
     */
    public static void registerParser(char character, CharacterParser parser) {
        PARSERS.put(character, parser);
    }
}