package no.uib.inf101.tetris.model;

import java.util.ArrayList;
import java.util.List;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel {

    private TetrisBoard tetrisBoard;
    private TetrominoFactory tetrominoFactory;
    private Tetromino tetromino;
    private GameState gameState;
    private Tetromino tetrominoShadow;
    private Tetromino tetrominoPreview1;
    private Tetromino tetrominoPreview2;
    private Tetromino tetrominoPreview3;
    private Tetromino tetrominoHold;
    private int points;
    private int totalRowsRemoved;

    public TetrisModel(TetrisBoard tetrisBoard, TetrominoFactory tetrominoFactory) {
        initializeModel(tetrisBoard, tetrominoFactory);
    }

    /**
     * Initialize model / resets model
     * 
     * @param tetrisBoard
     * @param tetrominoFactory
     */
    private void initializeModel(TetrisBoard tetrisBoard, TetrominoFactory tetrominoFactory) {
        this.tetrisBoard = tetrisBoard;
        this.tetrominoFactory = tetrominoFactory;
        this.tetromino = tetrominoFactory.getNext().shiftedToTopCenterOf(tetrisBoard);
        this.tetrominoPreview1 = tetrominoFactory.getNext();
        this.tetrominoPreview2 = tetrominoFactory.getNext();
        this.tetrominoPreview3 = tetrominoFactory.getNext();
        this.gameState = GameState.WELCOME;
        this.tetrominoShadow = this.tetromino;
        this.points = 0;
        this.totalRowsRemoved = 0;
        this.tetrominoHold = tetrominoFactory.getNext();
    }

    @Override
    public GridDimension getDimension() {
        return tetrisBoard;
    }

    @Override
    public Iterable<GridCell<Character>> getBoardTiles() {
        return tetrisBoard;
    }

    @Override
    public Iterable<GridCell<Character>> getFallenTiles() {
        return tetromino;
    }

    @Override
    public boolean moveTetromino(int deltaRow, int deltaCol) {
        Tetromino movedTetromino = this.tetromino.shiftedBy(deltaRow, deltaCol);
        if (isMoveValid(movedTetromino)) {
            this.tetromino = movedTetromino;
            updateShadow();
        } else {
            return false;
        }
        return isMoveValid(movedTetromino);
    }

    /**
     * Creates shadow for falling tetromino
     */

    private void updateShadow() {
        this.tetrominoShadow = this.tetromino.shadowColor();
        while (isMoveValid(this.tetrominoShadow.shiftedBy(1, 0))) {
            this.tetrominoShadow = this.tetrominoShadow.shiftedBy(1, 0);
        }
    }

    /**
     * 
     * @param tetromino
     * @return true if tetromino pos on board is valid, false othervise
     * 
     */
    private boolean isMoveValid(Tetromino tetromino) {
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetromino) {
            objs.add(gc);
        }
        for (int i = 0; i < objs.size(); i++) {
            CellPosition pos = objs.get(i).pos();
            if (!tetrisBoard.positionIsOnGrid(pos)) {
                return false;
            } else if (tetrisBoard.get(pos) != '-') {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean rotateTetromino() {
        Tetromino movedTetromino = this.tetromino.rotate();
        if (isMoveValid(movedTetromino)) {
            this.tetromino = movedTetromino;
            updateShadow();
        } else {
            return wallKick();
        }
        return isMoveValid(movedTetromino);
    }

    /**
     * Method checks if falling tetromino can be moved to complete rotation if, path
     * of rotation is obstucted.
     * Method takes I tetrominos into consideration by cheking if move move to times
     * to left is possible.
     * 
     * @return true if wallkick was sucsessfull, false othervise
     */
    private boolean wallKick() {
        Tetromino rotatedTetro = this.tetromino.rotate();

        if (kick(1, rotatedTetro)) {
            return true;
        } else if (tetromino.getChar().equals('I')) {
            if (kick(2, rotatedTetro)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Shifts tetronino by deltaCol if possible. Updates falling tetromino.
     * 
     * @param deltaCol
     * @param rotatedTetro
     * @return true if rotation with wallkick was complete, false othervise.
     */
    private boolean kick(int deltaCol, Tetromino rotatedTetro) {

        if (isMoveValid(rotatedTetro.shiftedBy(0, deltaCol))) {
            this.tetromino = rotatedTetro.shiftedBy(0, deltaCol);
            updateShadow();
            return true;
        } else if (isMoveValid(rotatedTetro.shiftedBy(0, -deltaCol))) {
            this.tetromino = rotatedTetro.shiftedBy(0, -deltaCol);
            updateShadow();
            return true;
        }
        return false;
    }

    @Override
    public void dropTetromino() {
        while (this.moveTetromino(1, 0)) {
            continue;
        }
        glueTetromino();
    }

    /**
     * Contructs new tetromino. If tetromino can be placed in center top of board,
     * all preniew tetrominos are updates, and tetromino on board is given value of
     * first preview. If not gamestate is set to GAME_OVER.
     */
    private void getNewFallingTetromino() {

        Tetromino newTetromino = this.tetrominoFactory.getNext();
        if (isMoveValid(newTetromino.shiftedToTopCenterOf(tetrisBoard))) {
            this.tetromino = tetrominoPreview1.shiftedToTopCenterOf(tetrisBoard);
            this.tetrominoPreview1 = this.tetrominoPreview2;
            this.tetrominoPreview2 = this.tetrominoPreview3;
            this.tetrominoPreview3 = newTetromino;
        } else {
            this.gameState = GameState.GAME_OVER;
        }
    }

    /**
     * Glues the fallen tetromino to the tetris board, removes full rows,
     * updates the score, and generates a new falling tetromino. Updates tetromino
     * shadow.
     */
    private void glueTetromino() {
        for (GridCell<Character> cell : this.getFallenTiles()) {
            CellPosition pos = cell.pos();
            Character value = cell.value();
            this.tetrisBoard.set(pos, value);
        }

        int rowsRemoved = this.tetrisBoard.removeFullRows();
        this.points += getlvl() * calculatePoints(rowsRemoved);
        this.totalRowsRemoved += rowsRemoved;
        getNewFallingTetromino();
        updateShadow();
    }

    @Override
    public GameState getGameState() {
        return this.gameState;
    }

    @Override
    public int getTimerDelay() {
        int delay = switch (this.getlvl()) {
            case 1 -> 1000;
            case 2 -> 700;
            case 3 -> 500;
            case 4 -> 200;
            case 5 -> 100;
            default -> 0;
        };
        return delay;

    }

    @Override
    public void clockTick() {
        if (!this.moveTetromino(1, 0)) {
            glueTetromino();
        }
    }

    @Override
    public Iterable<GridCell<Character>> getShadowTiles() {
        return this.tetrominoShadow;
    }

    /**
     * Calculates point based on rows removed at once
     * 
     * @param rowsRemoved
     * @return points
     */
    private int calculatePoints(int rowsRemoved) {
        int points = switch (rowsRemoved) {
            case 1 -> 100;
            case 2 -> 300;
            case 3 -> 500;
            case 4 -> 800;
            default -> 0;
        };
        return points;
    }

    @Override
    public String getPoints() {
        return Integer.toString(this.points);
    }

    @Override
    public int getlvl() {
        if (this.totalRowsRemoved < 5) {
            return 1;
        } else if (this.totalRowsRemoved < 10) {
            return 2;
        } else if (this.totalRowsRemoved < 20) {
            return 3;
        } else if (this.totalRowsRemoved < 50) {
            return 4;
        } else {
            return 5;
        }
    }

    @Override
    public Iterable<GridCell<Character>> getPreviewTiles(int num) {

        Iterable<GridCell<Character>> preview = switch (num) {
            case 0 -> this.tetrominoPreview1;
            case 1 -> this.tetrominoPreview2;
            case 2 -> this.tetrominoPreview3;
            default -> this.tetrominoHold;
        };
        return preview;
    }

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void restartModel() {
        TetrisBoard board = new TetrisBoard(20, 10);
        this.tetrisBoard = board;
        this.setGameState(GameState.ACTIVE_GAME);
    }

    @Override
    public void holdTetromino() {
        CellPosition pos = tetromino.getPos();

        if (isMoveValid(tetrominoHold.shiftedBy(pos.row(), pos.col()))) {

            Tetromino tempTetromino = this.tetrominoHold;

            Character c = tetromino.getChar();

            this.tetrominoHold = Tetromino.newTetromino(c);

            if (isMoveValid(tempTetromino.shiftedBy(pos.row(), pos.col()))) {
                this.tetromino = tempTetromino.shiftedBy(pos.row(), pos.col());
                updateShadow();
            } else if (isMoveValid(tempTetromino.shiftedBy(pos.row(), pos.col() - 1))) {
                this.tetromino = tempTetromino.shiftedBy(pos.row(), pos.col() - 1);
                updateShadow();
            } else if (isMoveValid(tempTetromino.shiftedBy(pos.row(), pos.col() + 1))) {
                this.tetromino = tempTetromino.shiftedBy(pos.row(), pos.col() + 1);
                updateShadow();
            }
        }
    }

    @Override
    public int getRemovedRows() {
        return this.totalRowsRemoved;
    }

}
