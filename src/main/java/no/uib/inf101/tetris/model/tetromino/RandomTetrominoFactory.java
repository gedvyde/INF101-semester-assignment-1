package no.uib.inf101.tetris.model.tetromino;

import java.util.Random;

public class RandomTetrominoFactory implements TetrominoFactory {

    @Override
    public Tetromino getNext() {
        Random rnd = new Random();
        String shapes = "LJSZTIO";
        int index = rnd.nextInt(shapes.length());
        Tetromino tetro = Tetromino.newTetromino(shapes.charAt(index));

        return tetro;
    }

}
