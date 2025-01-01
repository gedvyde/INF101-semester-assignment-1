package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;

public interface ViewableTetrisModel {

    GridDimension getDimension();

    /**
     * @return An iterable object representing all Gridcell<Character> object on the
     *         tetrisboard
     */
    Iterable<GridCell<Character>> getBoardTiles();

    /**
     * @return An iterable object representing all Gridcell<Character> object of
     *         falling tetromino
     */
    Iterable<GridCell<Character>> getFallenTiles();

    /**
     * @return An iterable object representing all Gridcell<Character> object of
     *         shadow of falling tetromino
     */
    Iterable<GridCell<Character>> getShadowTiles();

    /**
     * Select what preview by giving num (0-2) as arg to method.
     * 
     * @param num number of preview
     * @return An iterable object representing all Gridcell<Character> object of
     *         preview.
     */
    Iterable<GridCell<Character>> getPreviewTiles(int num);

    /**
     * @return current Game State
     */
    GameState getGameState();

    /**
     * @return points
     */
    String getPoints();

    /**
     * 
     * @return level
     */
    int getlvl();

    /**
     * @return removed rows
     */
    int getRemovedRows();
}
