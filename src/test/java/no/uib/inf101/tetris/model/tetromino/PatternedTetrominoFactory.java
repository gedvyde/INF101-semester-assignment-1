package no.uib.inf101.tetris.model.tetromino;

public class PatternedTetrominoFactory implements TetrominoFactory {
    
    private String s;
    private int index;

    public PatternedTetrominoFactory(String s) {
        this.s = s;
    }

    @Override
    public Tetromino getNext() {
        if (this.index == (s.length())) {
            this.index = 0;
        }
        return Tetromino.newTetromino(s.charAt(this.index++));
    }

    
}
