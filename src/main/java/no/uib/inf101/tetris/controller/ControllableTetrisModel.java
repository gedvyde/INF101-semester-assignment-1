package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;

public interface ControllableTetrisModel {

    /**
     * @param deltaRow
     * @param deltaCol
     * @return return true if move was done, if not false
     */
    boolean moveTetromino(int deltaRow, int deltaCol);

    /**
     * Creates new rotated Tetromino
     * Updates tetromino fieldvariabel is rotation is possible
     * 
     * @return true if rotation is possible, false otherwise
     */
    boolean rotateTetromino();

    /**
     * Moves tetromino one row down Tetrisboard,
     * as many times as possible.
     * When move down is not possible, Gridcell value
     * in Tetrisboard is updated and new Tetromino appears.
     * 
     * Rows are also deleted if they get full.
     * 
     * @return void
     */
    void dropTetromino();

    /**
     * @return current Game State
     */
    GameState getGameState();

    /**
     * Set gamestate of model to given gamestate
     * 
     * @param gameState
     * 
     */
    void setGameState(GameState gameState);

    /**
     * @return time between each tick
     */
    int getTimerDelay();

    /**
     * Moves falling tetromino down untill move is not
     * possible anymore.
     * 
     * When move down is not possible, Gridcell value
     * in Tetrisboard is updated and new Tetromino appears.
     * 
     */
    void clockTick();

    /**
     * Creates new Tetris board with 20 row, 15 cols.
     * Initializes fieldvariabel.
     * Updates gamestate to active.
     */
    void restartModel();


   
    /**
     * Exchanges fallen tetromino and tetromino in hold.
     */
    void holdTetromino();

    

}
