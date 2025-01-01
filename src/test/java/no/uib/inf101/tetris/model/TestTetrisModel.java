package no.uib.inf101.tetris.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.tetromino.PatternedTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TestTetrisModel {
    @Test
    public void initialPositionOfO() {
    TetrisBoard board = new TetrisBoard(20, 10);
    TetrominoFactory factory = new PatternedTetrominoFactory("O");
    ViewableTetrisModel model = new TetrisModel(board, factory);

    List<GridCell<Character>> tetroCells = new ArrayList<>();
    for (GridCell<Character> gc : model.getFallenTiles()) {
        tetroCells.add(gc);
    }

    assertEquals(4, tetroCells.size());
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'O')));
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'O')));
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
    }

    @Test
    public void initialPositionOfI() {
    TetrisBoard board = new TetrisBoard(20, 10);
    TetrominoFactory factory = new PatternedTetrominoFactory("I");
    ViewableTetrisModel model = new TetrisModel(board, factory);

    List<GridCell<Character>> tetroCells = new ArrayList<>();
    for (GridCell<Character> gc : model.getFallenTiles()) {
        tetroCells.add(gc);
    }

    assertEquals(4, tetroCells.size());
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'I')));
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'I')));
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 6), 'I')));
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 7), 'I')));
    }


    @Test
    public void testmoveTetromino() {
        TetrisBoard board = new TetrisBoard(20, 10);
        board.set(new CellPosition(0, 0), 'r');
        TetrominoFactory factory = new PatternedTetrominoFactory("O");
        TetrisModel model = new TetrisModel(board, factory);
        
        assertTrue(model.moveTetromino(0, 1)); // moves one collumn to rigth
        assertTrue(model.moveTetromino(0, -1)); // moves one collumn to left

        assertTrue(!model.moveTetromino(30, 0)); // out off grid
        
        assertTrue(!model.moveTetromino(0, -4)); // square in Cellposition(0,0)

    }

    @Test
    public void testDropTetromino() {
        TetrisBoard board = new TetrisBoard(20, 10);
        board.set(new CellPosition(0, 0), 'r');
        TetrominoFactory factory = new PatternedTetrominoFactory("I");
        TetrisModel model = new TetrisModel(board, factory);
        
        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallenTiles()) {
            tetroCells.add(gc);
        }
      
        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 6), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 7), 'I')));

        model.dropTetromino(); 

        List<GridCell<Character>> tetroCellsAfterDrop = new ArrayList<>();
        for (GridCell<Character> gc : model.getBoardTiles()) {
            tetroCellsAfterDrop.add(gc);
        }

        assertTrue(tetroCellsAfterDrop.contains(new GridCell<>(new CellPosition(19, 4), 'I')));
        assertTrue(tetroCellsAfterDrop.contains(new GridCell<>(new CellPosition(19, 5), 'I')));
        assertTrue(tetroCellsAfterDrop.contains(new GridCell<>(new CellPosition(19, 6), 'I')));
        assertTrue(tetroCellsAfterDrop.contains(new GridCell<>(new CellPosition(19, 7), 'I')));
    }

    @Test
    public void testClockTick() {
        TetrisBoard board = new TetrisBoard(20, 10);
        board.set(new CellPosition(0, 0), 'r');
        TetrominoFactory factory = new PatternedTetrominoFactory("I");
        TetrisModel model = new TetrisModel(board, factory);
        
        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallenTiles()) {
            tetroCells.add(gc);
        }
      
        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 6), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 7), 'I')));

        model.clockTick(); 

        List<GridCell<Character>> tetroCellsAfterDrop = new ArrayList<>();
        for (GridCell<Character> gc : model.getBoardTiles()) {
            tetroCellsAfterDrop.add(gc);
        }

        assertTrue(tetroCellsAfterDrop.contains(new GridCell<>(new CellPosition(19, 4), 'I')));
        assertTrue(tetroCellsAfterDrop.contains(new GridCell<>(new CellPosition(19, 5), 'I')));
        assertTrue(tetroCellsAfterDrop.contains(new GridCell<>(new CellPosition(19, 6), 'I')));
        assertTrue(tetroCellsAfterDrop.contains(new GridCell<>(new CellPosition(19, 7), 'I')));
    }

    
}
