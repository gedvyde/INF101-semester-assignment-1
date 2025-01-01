package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;

public class TetrisBoard extends Grid<Character> {

    public TetrisBoard(int rows, int cols) {
        super(rows, cols, '-');
    }

    /**
     * Makes String representation of Tetrisboard.
     * Each row is split by \n
     * 
     * @return String
     */
    public String prettyString() {
        String s = "";
        for (int r = 0; r < this.rows(); r++) {
            for (int c = 0; c < this.cols(); c++) {
                Character ch = this.get(new CellPosition(r, c));
                s += ch;
            }
            if (r == this.rows() - 1) {
                continue;
            }
            s += new String("\n");
        }
        return s;
    }

    /**
     * @param element
     * @param row
     * @return true if element is in row, false othervise
     */
    private boolean exsistOnRow(Character element, int row) {
        for (int col = 0; col < this.cols(); col++) {
            if (this.get(new CellPosition(row, col)) == element)
                return true;
        }
        return false;
    }

    /**
     * Set all cols in row to given element
     * 
     * @param element
     * @param row
     */
    private void setARow(Character element, int row) {
        for (int col = 0; col < this.cols(); col++) {
            this.set(new CellPosition(row, col), element);
        }
    }

    /**
     * @param copyRow
     * @param pasteRow
     *                 Takes elements in copyRow and sets corresponding col in new
     *                 row to this element
     */
    private void copyPasteRow(int copyRow, int pasteRow) {
        for (int col = 0; col < this.cols(); col++) {
            Character element = this.get(new CellPosition(copyRow, col));
            this.set(new CellPosition(pasteRow, col), element);
        }
    }

    /**
     * Removes full rows from the Tetris board and adjusts the board accordingly.
     * This method scans the board from bottom to top, identifies full rows,
     * and removes them by shifting the rows above down.
     * 
     * @return The number of full rows removed from the board.
     */
    public int removeFullRows() {
        int counter = 0;
        int a = this.rows() - 1;
        int b = this.rows() - 1;

        while (a >= 0) {
            while (b >= 0 && !exsistOnRow('-', b)) {
                counter++;
                b--;
            }
            if (b >= 0) {
                copyPasteRow(b, a);

            } else {
                setARow('-', a);
            }
            a--;
            b--;
        }

        return counter;
    }

    /**
     * Takes list with String (String[] stringboard)
     * and converts to corresponding Tetrisboard
     * 
     * @param stringBoard
     * @return Tetrisboard
     */
    public static TetrisBoard getTetrisBoardWithContents(String[] stringBoard) {
        TetrisBoard board = new TetrisBoard(stringBoard.length, stringBoard[0].length());
        for (int r = 0; r < stringBoard.length; r++) {
            String stringRow = stringBoard[r];
            for (int c = 0; c < stringRow.length(); c++) {
                CellPosition pos = new CellPosition(r, c);
                Character value = stringRow.charAt(c);
                board.set(pos, value);
            }
        }
        return board;
    }

}
