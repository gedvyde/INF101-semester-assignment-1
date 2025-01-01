package no.uib.inf101.tetris;

import javax.swing.JFrame;

import no.uib.inf101.tetris.controller.TetrisController;
import no.uib.inf101.tetris.model.TetrisBoard;
import no.uib.inf101.tetris.model.TetrisModel;
import no.uib.inf101.tetris.model.tetromino.RandomTetrominoFactory;
import no.uib.inf101.tetris.view.TetrisView;

/**
 * @author Gedvyde of all implemented methods
 */
public class TetrisMain {
  public static final String WINDOW_TITLE = "INF101 Tetris";

  public static void main(String[] args) {
    TetrisBoard board = new TetrisBoard(20, 10);
    RandomTetrominoFactory factory = new RandomTetrominoFactory();
    TetrisModel model = new TetrisModel(board, factory);
    TetrisView view = new TetrisView(model);
    TetrisController controller = new TetrisController(model, view);

    JFrame frame = new JFrame(WINDOW_TITLE);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);
  }

}
