package no.uib.inf101.tetris.view;

import java.awt.Color;

import no.uib.inf101.tetris.model.GameState;

public interface ColorTheme {

    /**
     * @param Character represents color of cell
     * @return Color value for the given character.
     *         Can not return null.
     * @throws IllegalArgumentException if Color for character is not defined.
     *
     */
    Color getCellColor(Character c);

    /**
     * @return Color value for frame.
     *         Can not return null.
     */
    Color getFrameColor();

    /**
     * @return Color value for background.
     *         Can return null -> standard java Color
     *         Can not be transperent.
     */
    Color getBackgroundColor();

    /**
     * @param gameState
     * @return Color value for given gamestate
     */
    Color getGameStateColor(GameState gameState);

    /**
     * @return Color value for Title
     */
    Color getTitleColor();
}
