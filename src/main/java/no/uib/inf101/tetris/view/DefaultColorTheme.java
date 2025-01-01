package no.uib.inf101.tetris.view;

import java.awt.Color;

import no.uib.inf101.tetris.model.GameState;

public class DefaultColorTheme implements ColorTheme {

    @Override
    public Color getCellColor(Character c) {
        Color color = switch (c) {
            case '-' -> Color.BLACK;
            case 'x' -> Color.LIGHT_GRAY;
            case 'r' -> Color.RED;
            case 'I' -> Color.CYAN;
            case 'J' -> Color.BLUE;
            case 'L' -> Color.ORANGE;
            case 'O' -> Color.YELLOW;
            case 'S' -> Color.GREEN;
            case 'T' -> new Color(255, 0, 255);
            case 'Z' -> Color.RED;
            default -> throw new IllegalArgumentException(
                    "No available color for '" + c + "'");
        };
        return color;
    }

    @Override
    public Color getFrameColor() {
        return Color.DARK_GRAY;
    }

    @Override
    public Color getBackgroundColor() {
        return Color.BLACK;
    }

    @Override
    public Color getGameStateColor(GameState gameState) {
        Color color = switch (gameState) {
            case GAME_OVER -> new Color(0, 0, 0, 128);
            case WELCOME -> new Color(246, 246, 246);
            case PAUSE -> new Color(0, 0, 0, 128);
            default -> throw new IllegalArgumentException(
                    "No available color for '" + gameState + "'");
        };
        return color;
    }

    @Override
    public Color getTitleColor() {
        return Color.RED;
    }

}
