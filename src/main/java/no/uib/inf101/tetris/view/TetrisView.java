package no.uib.inf101.tetris.view;

import javax.swing.JPanel;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.model.TetrisBoard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class TetrisView extends JPanel {

  private ViewableTetrisModel tetrisModel;
  private ColorTheme colorTheme;
  private Graphics2D graphics2d;
  private Rectangle2D gameWindow;
  private int sizeScalar;

  private static final int OUTERMARGIN = 20;
  private static final double INNERMARGIN = 4;
  private final String FONT = "Britannic Bold";

  public TetrisView(ViewableTetrisModel tetrisModel) {
    this.tetrisModel = tetrisModel;
    this.colorTheme = new DefaultColorTheme();
    this.setPreferredSize(new Dimension(500, 700));
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.graphics2d = (Graphics2D) g;
    drawGame();
  }

  private void drawGame() {
    double textSpace = getHeight() / 5;
    double sideSpace = getWidth() / 5;
    double width = getWidth() - OUTERMARGIN * 2 - sideSpace;
    double height = getHeight() - textSpace;
 

    this.sizeScalar = (int) (Math.pow((Math.pow(width, 2) + Math.pow(textSpace, 2)), 0.3));
    this.gameWindow = new Rectangle2D.Double(OUTERMARGIN+sideSpace/2, textSpace-OUTERMARGIN, width-sideSpace*2/3, height);

    CellPositionToPixelConverter converter = new CellPositionToPixelConverter(gameWindow, tetrisModel.getDimension(),
        INNERMARGIN);

    // Draw title
    graphics2d.setColor(colorTheme.getTitleColor());
    graphics2d.setFont(new Font(FONT, Font.BOLD, sizeScalar * 2));
    graphics2d.drawString("TETRIS", OUTERMARGIN, (int) textSpace * 2 / 3);
    // Draw Scores
    graphics2d.setFont(new Font(FONT, Font.BOLD, sizeScalar / 2));
    String score = ("POINTS: " + tetrisModel.getPoints()) + ("  LVL: " + Integer.toString(tetrisModel.getlvl()));
    Inf101Graphics.drawCenteredString(graphics2d, score, getWidth() * 3 / 4, textSpace / 2);
    score = "ROWS REMOVED: " + tetrisModel.getRemovedRows();
    Inf101Graphics.drawCenteredString(graphics2d, score, getWidth() * 3 / 4, textSpace / 2 - sizeScalar / 2);

    // Draw frame
    graphics2d.setColor(colorTheme.getFrameColor());
    graphics2d.fill(gameWindow);
    // Draw board
    drawCells(graphics2d, tetrisModel.getBoardTiles(), converter, colorTheme, true);
    // Draw Tetromino shadow
    drawCells(graphics2d, tetrisModel.getShadowTiles(), converter, colorTheme, true);
    // Draw Tetromino
    drawCells(graphics2d, tetrisModel.getFallenTiles(), converter, colorTheme, true);

    // Draw Tetromino preview
    for (int i = 0; i < 4; i++) {
      Rectangle2D previewBox = new Rectangle2D.Double(width + OUTERMARGIN * 2,
          textSpace + OUTERMARGIN + (sizeScalar * 2 + OUTERMARGIN) * i, sizeScalar * 2, sizeScalar * (2));
      graphics2d.setColor(colorTheme.getFrameColor());
      graphics2d.fill(previewBox);

      TetrisBoard previewBoard = new TetrisBoard(4, 4);
      CellPositionToPixelConverter previewConverter = new CellPositionToPixelConverter(previewBox, previewBoard,
          INNERMARGIN);

      drawCells(graphics2d, previewBoard, previewConverter, colorTheme, false);
      drawCells(graphics2d, tetrisModel.getPreviewTiles(i), previewConverter, colorTheme, false);

      graphics2d.setFont(new Font(FONT, Font.BOLD, sizeScalar * 3 / 8));
      graphics2d.setColor(colorTheme.getTitleColor());
      if (!(i == 3)) {
        Inf101Graphics.drawCenteredString(graphics2d, "PREVIEW: " + (i + 1), width + OUTERMARGIN * 2,
            textSpace + (sizeScalar * 2 + OUTERMARGIN) * i, sizeScalar * 2, OUTERMARGIN);
      } else {
        Inf101Graphics.drawCenteredString(graphics2d, "HOLD (H): ", width + OUTERMARGIN * 2,
            textSpace + (sizeScalar * 2 + OUTERMARGIN) * i, sizeScalar * 2, OUTERMARGIN);
        Inf101Graphics.drawCenteredString(graphics2d, "PAUSE (P) ", width + OUTERMARGIN * 2,
            textSpace + (sizeScalar * 2 + OUTERMARGIN) * (i + 2), sizeScalar * 2, OUTERMARGIN);
      }

    }

    // Draw gamestate grafics
    drawGameState(tetrisModel.getGameState());
  }

  private static void drawCells(Graphics2D g2, Iterable<GridCell<Character>> iterable,
      CellPositionToPixelConverter converter, ColorTheme colorTheme, Boolean roundCorners) {
    for (GridCell<Character> gridCell : iterable) {
      CellPosition pos = gridCell.pos();
      Character c = gridCell.value();
      Color color = colorTheme.getCellColor(c);
      Shape cellSquare = converter.getBoundsForCell(pos, roundCorners);
      g2.setColor(color);
      g2.fill(cellSquare);
    }
  }

  private void drawGameState(GameState gameState) {

    if (tetrisModel.getGameState() == GameState.PAUSE) {
      // Blur gamescreen
      graphics2d.setColor(colorTheme.getGameStateColor(tetrisModel.getGameState()));
      graphics2d.fill(gameWindow);

    }

    if (tetrisModel.getGameState() == GameState.WELCOME) {
      // Color background to match gameboy
      Shape screen = new Rectangle(0, 0, getWidth(), getHeight());
      graphics2d.setColor(colorTheme.getGameStateColor(tetrisModel.getGameState()));
      graphics2d.fill(screen);
      // Draw gameboy image
      BufferedImage image = Inf101Graphics.loadImageFromResources("/gameboy.png");
      Inf101Graphics.drawCenteredImage(graphics2d, image, getWidth() / 2, getHeight() / 2, 0.6);
      // Draw frame for title
      Rectangle2D gameboyScreen = new Rectangle2D.Double(OUTERMARGIN, 0, getWidth() - 2 * OUTERMARGIN, getHeight() / 2);
      graphics2d.setColor(colorTheme.getFrameColor());
      graphics2d.fill(gameboyScreen);
      // Draw title
      TetrisBoard gameboyBoard = TetrisBoard.getTetrisBoardWithContents(new String[] {
          "----------------------------------------------",
          "----------------------------------------------",
          "----------------------------------------------",
          "----------------------------------------------",
          "----------------------------------------------",
          "-SS--SSTTT-OOJJJ--SS--ZZTTT--ZJJJ-IIIIZZ--IIII",
          "SSJJJISST--OOZ-J-SSJJJIZZT--ZZ--J--OOZZ--OOJ--",
          "----JI------ZZ-------JI-----ZT-OO--OOJJ--OOJ--",
          "----LI------ZLLL-----LI-----TT-OO---LJ----JJSS",
          "----LI------SL-------LI-----ITLLL---LJ-----SSZ",
          "----LL-----TSS-------LL-----I-LS----LL------ZZ",
          "----OO-----TTS-------OO-----I--SS--TZZ---TTTZL",
          "----OO-----TIIII-----OO-----I---S-TTTZZ---TLLL",
          "----------------------------------------------",
          "----------------------------------------------",
          "----------------------------------------------",
          "----------------------------------------------",
      });
      CellPositionToPixelConverter gameboyConverter = new CellPositionToPixelConverter(gameboyScreen, gameboyBoard, 2);
      drawCells(graphics2d, gameboyBoard, gameboyConverter, colorTheme, true);
      // Draw info text
      graphics2d.setColor(colorTheme.getTitleColor());
      graphics2d.setFont(new Font(FONT, Font.BOLD, (sizeScalar / 2)));
      Inf101Graphics.drawCenteredString(graphics2d, "PRESS DOWN TO PLAY", getWidth() / 2,
          getHeight() / 2 + sizeScalar);
    }

    if (tetrisModel.getGameState() == GameState.GAME_OVER) {
      // Blur gamescreen
      graphics2d.setColor(colorTheme.getGameStateColor(tetrisModel.getGameState()));
      graphics2d.fill(gameWindow);
      // Draw gameover text
      graphics2d.setColor(colorTheme.getTitleColor());
      graphics2d.setFont(new Font(FONT, Font.BOLD, (sizeScalar)));
      Inf101Graphics.drawCenteredString(graphics2d, "GAME OVER", gameWindow);
      graphics2d.setFont(new Font(FONT, Font.BOLD, (sizeScalar / 2)));
      Inf101Graphics.drawCenteredString(graphics2d, "PRESS DOWN TO PLAY AGAIN", gameWindow.getCenterX(),
          gameWindow.getCenterY() + sizeScalar);
    }
  }
}
