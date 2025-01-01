package no.uib.inf101.tetris.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.tetris.model.tetromino.PatternedTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;

public class TestTetrisBoard {
    
    @Test
    public void prettyStringTest() {
    TetrisBoard board = new TetrisBoard(3, 4);
    board.set(new CellPosition(0, 0), 'g');
    board.set(new CellPosition(0, 3), 'y');
    board.set(new CellPosition(2, 0), 'r');
    board.set(new CellPosition(2, 3), 'b');
    String expected = String.join("\n", new String[] {
        "g--y",
        "----",
        "r--b"
    });
    assertEquals(expected, board.prettyString());
    }


    /**
     * Takes list with String (String[] stringboard) 
     * and converts to corresponding Tetrisboard
     * @param stringBoard
     * @return Tetrisboard
     */
    public TetrisBoard getTetrisBoardWithContents(String[] stringBoard) {
        TetrisBoard board = new TetrisBoard(stringBoard.length, stringBoard[0].length());
        for (int r = 0; r<stringBoard.length; r++) {
            String stringRow = stringBoard[r];
            for (int c = 0; c<stringRow.length(); c++) {
                CellPosition pos = new CellPosition(r, c);
                Character value = stringRow.charAt(c);
                board.set(pos, value);
            }
        }
        return board;
    }

    @Test
    public void testRemoveFullRows() {
    TetrisBoard board = getTetrisBoardWithContents(new String[] {
        "-T",
        "TT",
        "LT",
        "L-",
        "LL"
    });
    assertEquals(3, board.removeFullRows());
    String expected = String.join("\n", new String[] {
        "--",
        "--",
        "--",
        "-T",
        "L-"
    });
    assertEquals(expected, board.prettyString());
    }

    @Test
    public void testLastRowIsKept() {
        //En test der nederste rad må beholdes
        TetrisBoard board = getTetrisBoardWithContents(new String[] {
            "T-",
            "LT",
            "TT",
            "LL",
            "L-"
        });
        assertEquals(3, board.removeFullRows());
        String expected = String.join("\n", new String[] {
            "--",
            "--",
            "--",
            "T-",
            "L-"
        });
        assertEquals(expected, board.prettyString());

    }

    @Test
    public void testFirstRowIsRemoved() {
        //En test der nederste rad må beholdes
        TetrisBoard board = getTetrisBoardWithContents(new String[] {
            "TT",
            "LT",
            "TT",
            "LL",
            "L-"
        });
        assertEquals(4, board.removeFullRows());
        String expected = String.join("\n", new String[] {
            "--",
            "--",
            "--",
            "--",
            "L-"
        });
        assertEquals(expected, board.prettyString());

    }

    @Test
    public void testBiggerBoard() {
        //En test der nederste rad må beholdes
        TetrisBoard board = getTetrisBoardWithContents(new String[] {
            "TTTT",
            "LTLL",
            "TT--",
            "LLL-",
            "L---"
        });
        assertEquals(2, board.removeFullRows());
        String expected = String.join("\n", new String[] {
            "----",
            "----",
            "TT--",
            "LLL-",
            "L---"
        });
        assertEquals(expected, board.prettyString());

    }

    @Test
    public void testPoints() {
        //En test der nederste rad må beholdes
        TetrisBoard board = getTetrisBoardWithContents(new String[] {
            "----",
            "----",
            "TTTT",
            "LLTL",
            "L---"
        });

        TetrominoFactory factory = new PatternedTetrominoFactory("S");
        TetrisModel model = new TetrisModel(board, factory);

        model.clockTick();

        String expected = String.join("\n", new String[] {
            "----",
            "----",
            "-SS-",
            "SS--",
            "L---"
        });
        assertEquals(expected, board.prettyString());
        assertEquals("300",model.getPoints());
    }
}
