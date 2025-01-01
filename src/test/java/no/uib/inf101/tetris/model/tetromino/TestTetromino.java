package no.uib.inf101.tetris.model.tetromino;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;

import no.uib.inf101.grid.GridCell;

import no.uib.inf101.tetris.model.TetrisBoard;

public class TestTetromino {

    @Test
    public void testHashCodeAndEquals() {
    Tetromino t1 = Tetromino.newTetromino('T');
    Tetromino t2 = Tetromino.newTetromino('T');
    Tetromino t3 = Tetromino.newTetromino('T').shiftedBy(1, 0);
    Tetromino s1 = Tetromino.newTetromino('S');
    Tetromino s2 = Tetromino.newTetromino('S').shiftedBy(0, 0);

    assertEquals(t1, t2);
    assertEquals(s1, s2);
    assertEquals(t1.hashCode(), t2.hashCode());
    assertEquals(s1.hashCode(), s2.hashCode());
    assertNotEquals(t1, t3);
    assertNotEquals(t1, s1);
    }

    @Test
    public void tetrominoIterationOfT() {
    // Create a standard 'T' tetromino placed at (10, 100) to test
    Tetromino tetro = Tetromino.newTetromino('T');
    tetro = tetro.shiftedBy(10, 100);

    // Collect which objects are iterated through
    List<GridCell<Character>> objs = new ArrayList<>();
    for (GridCell<Character> gc : tetro) {
        objs.add(gc);
    }

    // Check that we got the expected GridCell objects
    assertEquals(4, objs.size());
    assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 100), 'T')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 101), 'T')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 102), 'T')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 101), 'T')));
    }

    @Test
    public void tetrominoIterationOfS() {
        // Create a standard 'S' tetromino placed at (10, 100) to test
    Tetromino tetro = Tetromino.newTetromino('S');
    tetro = tetro.shiftedBy(10, 100);

    // Collect which objects are iterated through
    List<GridCell<Character>> objs = new ArrayList<>();
    for (GridCell<Character> gc : tetro) {
        objs.add(gc);
    }

    // Check that we got the expected GridCell objects
    assertEquals(4, objs.size());
    assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 101), 'S')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 102), 'S')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 100), 'S')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 101), 'S')));
    }
    

    @Test
    public void shiftedDoubleLenght() {
        Tetromino tetro1 = Tetromino.newTetromino('S');
        tetro1 = tetro1.shiftedBy(10, 100);
        Tetromino tetro2 = Tetromino.newTetromino('S');
        tetro2 = tetro2.shiftedBy(5, 50);
        tetro2 = tetro2.shiftedBy(5, 50);


            // Collect which objects are iterated through
        List<GridCell<Character>> objs1 = new ArrayList<>();
        for (GridCell<Character> gc : tetro1) {
            objs1.add(gc);
        }

            // Collect which objects are iterated through
        List<GridCell<Character>> objs2 = new ArrayList<>();
        for (GridCell<Character> gc : tetro2) {
            objs2.add(gc);
        }
        assertEquals(objs1.size(), objs2.size());
        assertTrue(objs1.contains(new GridCell<>(new CellPosition(11, 101), 'S')));
        assertTrue(objs1.contains(new GridCell<>(new CellPosition(11, 102), 'S')));
        assertTrue(objs1.contains(new GridCell<>(new CellPosition(12, 100), 'S')));
        assertTrue(objs1.contains(new GridCell<>(new CellPosition(12, 101), 'S')));
        assertTrue(objs2.contains(new GridCell<>(new CellPosition(11, 101), 'S')));
        assertTrue(objs2.contains(new GridCell<>(new CellPosition(11, 102), 'S')));
        assertTrue(objs2.contains(new GridCell<>(new CellPosition(12, 100), 'S')));
        assertTrue(objs2.contains(new GridCell<>(new CellPosition(12, 101), 'S')));
    }

    @Test
    public void testShiftedToTopCenterOf() {
        Tetromino tetro = Tetromino.newTetromino('S');
        TetrisBoard board = new TetrisBoard(10, 10);
      
        tetro = tetro.shiftedToTopCenterOf(board);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
            
        }

        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 5), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 6), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 4), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 5), 'S')));


        Tetromino tetro1 = Tetromino.newTetromino('O');
        tetro1 = tetro1.shiftedToTopCenterOf(board);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs1 = new ArrayList<>();
        for (GridCell<Character> gc : tetro1) {
            objs1.add(gc);            
        }

        assertTrue(objs1.contains(new GridCell<>(new CellPosition(0, 5), 'O')));
        assertTrue(objs1.contains(new GridCell<>(new CellPosition(0, 6), 'O')));
        assertTrue(objs1.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
        assertTrue(objs1.contains(new GridCell<>(new CellPosition(1, 6), 'O')));

    }

    @Test
    public void testRotation() {
        Tetromino tetro = Tetromino.newTetromino('S');
        
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);            
        }

        tetro = tetro.rotate().rotate().rotate().rotate();

        List<GridCell<Character>> objs1 = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs1.add(gc);            
        }
        //tetromino returns to same place after 4 rotations
        assertTrue(objs1.hashCode() == objs.hashCode());

        Tetromino tetro1 = Tetromino.newTetromino('O');

        //tetromino with shape "O" has same values after rotation
        assertTrue(tetro1.hashCode() == tetro1.rotate().hashCode());
    }
}
