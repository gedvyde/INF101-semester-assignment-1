package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.midi.TetrisSong;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.view.TetrisView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class TetrisController implements KeyListener {

    private ControllableTetrisModel model;
    private TetrisView tetrisView;
    private Timer timer;
    private TetrisSong song;

    public TetrisController(ControllableTetrisModel model, TetrisView tetrisView) {
        this.model = model;
        this.tetrisView = tetrisView;
        this.tetrisView.addKeyListener(this);
        this.tetrisView.setFocusable(true);
        this.timer = new Timer(model.getTimerDelay(), this::clockTick);
        this.updateDelay();
        this.timer.start();
        this.song = new TetrisSong();
        this.song.run();
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (!(this.model.getGameState() == GameState.PAUSE)) {
            if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
                this.model.moveTetromino(0, -1);
            } else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
                this.model.moveTetromino(0, 1);
            } else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
                if (this.model.moveTetromino(1, 0)) {
                    this.timer.restart();
                }
                if (this.model.getGameState() == GameState.GAME_OVER) {
                    this.model.restartModel();
                }
                if ((this.model.getGameState() == GameState.WELCOME)) {
                    this.model.setGameState(GameState.ACTIVE_GAME);
                }
            } else if (arg0.getKeyCode() == KeyEvent.VK_UP) {
                this.model.rotateTetromino();
            } else if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
                this.model.dropTetromino();
                this.timer.restart();
            } else if (arg0.getKeyCode() == KeyEvent.VK_H){
                this.model.holdTetromino();
            }  
        }
        if (arg0.getKeyCode() == KeyEvent.VK_P) {
            if (this.model.getGameState() == GameState.PAUSE) {
                    this.model.setGameState(GameState.ACTIVE_GAME);
                    this.timer.start();
                } else {
                    this.model.setGameState(GameState.PAUSE);
                    this.timer.stop();
                }
        }
    
                
        this.tetrisView.repaint();
    }

    /**
     * Handles the ticking of the clock
     * This method is invoked when an ActionEvent is triggered.
     *
     * @param ActionEvent that triggers the clock tick.
     */

    private void clockTick(ActionEvent ActionEvent) {
        if ((this.model.getGameState() == GameState.ACTIVE_GAME)) {
            this.model.clockTick();
            this.updateDelay();
            this.tetrisView.repaint();
        }
    }

    /**
     * @return time delay of model
     */
    private int getDelay() {
        return this.model.getTimerDelay();
    }

    /**
     * Sets timer delay with retrieved time delay of model
     */
    private void updateDelay() {
        timer.setDelay(this.getDelay());
        timer.setInitialDelay(this.getDelay());
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

}
