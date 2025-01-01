package no.uib.inf101.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

public class Tetromino implements Iterable<GridCell<Character>> {
    private char character;
    private boolean[][] shape;
    private CellPosition pos;

    /**
     * Want to controll what tetromino are created, constructor is therefore
     * private.
     * Constructor is called in newTetromino method.
     * 
     * @param character
     * @param shape
     * @param pos
     */
    private Tetromino(char character, boolean[][] shape, CellPosition pos) {
        this.character = character;
        this.shape = shape;
        this.pos = pos;

    }

    /**
     * 
     * Constructs new tetromino with valid shape and pos (0,0) on grid.
     * 
     * @param character
     * @return Tetromino
     * @throws IllegalArgumentException if shape is not available for given
     *                                  character
     */
    public static Tetromino newTetromino(char character) throws IllegalArgumentException {

        CellPosition pos = new CellPosition(0, 0);

        boolean[][] shape = switch (character) {
            case 'T' -> new boolean[][] {
                    { false, false, false },
                    { true, true, true },
                    { false, true, false }
            };
            case 'L' -> new boolean[][] {
                    { false, false, false },
                    { true, true, true },
                    { true, false, false }
            };
            case 'J' -> new boolean[][] {
                    { false, false, false },
                    { true, true, true },
                    { false, false, true }
            };
            case 'S' -> new boolean[][] {
                    { false, false, false },
                    { false, true, true },
                    { true, true, false }
            };
            case 'Z' -> new boolean[][] {
                    { false, false, false },
                    { true, true, false },
                    { false, true, true }
            };
            case 'I' -> new boolean[][] {
                    { false, false, false, false },
                    { true, true, true, true },
                    { false, false, false, false },
                    { false, false, false, false },
            };
            case 'O' -> new boolean[][] {
                    { false, false, false, false },
                    { false, true, true, false },
                    { false, true, true, false },
                    { false, false, false, false },
            };
            default -> throw new IllegalArgumentException(
                    "Shape not valid");
        };

        return new Tetromino(character, shape, pos);
    }

    /**
     * Constructs new tetromino with row and col value of pos to tetromino is updated with corresponding args.
     * @param deltaRow
     * @param deltaCol
     * @return new Tetromino with updated pos values
     */
    public Tetromino shiftedBy(int deltaRow, int deltaCol) {
        CellPosition pos = this.pos;
        int row = pos.row() + deltaRow;
        int col = pos.col() + deltaCol;
        CellPosition shiftedPos = new CellPosition(row, col);
        Tetromino shiftedTetromino = new Tetromino(this.character, this.shape, shiftedPos);
        return shiftedTetromino;
    }

    public Tetromino shiftedToTopCenterOf(GridDimension gridDimension) {
        int cols = gridDimension.cols() / 2 - 1;
        int shapeCol = this.iterator().next().pos().col();
        int shapeRow = this.iterator().next().pos().row();

        CellPosition shiftedPos = new CellPosition(0 - shapeRow, cols - shapeCol);

        Tetromino shiftedTetromino = new Tetromino(this.character, this.shape, shiftedPos);
        return shiftedTetromino;
    }

    @Override
    public Iterator<GridCell<Character>> iterator() {
        ArrayList<GridCell<Character>> usedGridcell = new ArrayList<>();
        int row = this.pos.row();
        int col = this.pos.col();

        int shapeLenght = this.shape.length;
        for (int r = 0; r < shapeLenght; r++) {
            for (int c = 0; c < shapeLenght; c++) {
                if (shape[r][c] == true) {
                    CellPosition cellPos = new CellPosition(row + r, col + c);
                    usedGridcell.add(new GridCell<Character>(cellPos, this.character));
                }
            }
        }
        return usedGridcell.iterator();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + character;
        result = prime * result + Arrays.deepHashCode(shape);
        result = prime * result + ((pos == null) ? 0 : pos.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tetromino other = (Tetromino) obj;
        if (character != other.character)
            return false;
        if (!Arrays.deepEquals(shape, other.shape))
            return false;
        if (pos == null) {
            if (other.pos != null)
                return false;
        } else if (!pos.equals(other.pos))
            return false;
        return true;
    }

    public Tetromino rotate() {
        int lenght = this.shape.length;

        boolean[][] newShape = new boolean[lenght][lenght];

        for (int r = 0; r < lenght; r++) {
            for (int c = 0; c < lenght; c++) {
                if (shape[r][c]) {
                    newShape[lenght - c - 1][r] = true;
                } else {
                    newShape[lenght - c - 1][r] = false;
                }
            }
        }
        return new Tetromino(this.character, newShape, pos);
    }
    



    /**
     * @return new Tetromino with shadow color
     */
    public Tetromino shadowColor() {
        return new Tetromino('x', shape, pos);
    }


    public Character getChar() {
        return this.character;
    }

    public CellPosition getPos() {
        return this.pos;
    }
}
