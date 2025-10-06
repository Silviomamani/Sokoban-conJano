package manager;

import model.GameBoard;
import model.elements.Player;
import model.factory.boxesfactory.BoxFactory;
import model.factory.cellsfactory.CellFactory;
import java.io.*;
import java.util.*;

// BUILDER: Construye niveles desde archivos .txt
public class LevelBuilder {

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
            // Si no encuentra el recurso, crear nivel por defecto
            return createDefaultLevelData();
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
        CellFactory cellFactory;
        BoxFactory boxFactory;

        switch (c) {
            case '#': // Pared
                cellFactory = CellFactory.getFactory("wall");
                board.setCell(x, y, cellFactory.createCell(x, y));
                break;
            case '.': // Objetivo
                cellFactory = CellFactory.getFactory("target");
                board.setCell(x, y, cellFactory.createCell(x, y));
                break;
            case 'C': // Checkpoint
                cellFactory = CellFactory.getFactory("checkpoint");
                board.setCell(x, y, cellFactory.createCell(x, y));
                break;
            case 'I': // Ice/Slippery
                cellFactory = CellFactory.getFactory("slippery");
                board.setCell(x, y, cellFactory.createCell(x, y));
                break;
            case 'L': // Lock (ID 0)
                cellFactory = CellFactory.getFactory("lock");
                board.setCell(x, y, cellFactory.createCell(x, y, 0));
                break;
            case 'B': // Caja normal
                boxFactory = BoxFactory.getFactory("normal");
                board.addBox(boxFactory.createBox(x, y));
                break;
            case 'X': // Caja bomba
                boxFactory = BoxFactory.getFactory("bomb");
                board.addBox(boxFactory.createBox(x, y));
                break;
            case 'K': // Caja llave (ID 0)
                boxFactory = BoxFactory.getFactory("key");
                board.addBox(boxFactory.createBox(x, y, 0));
                break;
            case 'P': // Jugador
                board.setPlayer(new Player(x, y));
                break;
            case ' ': // Espacio vacío
            default:
                cellFactory = CellFactory.getFactory("empty");
                board.setCell(x, y, cellFactory.createCell(x, y));
                break;
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