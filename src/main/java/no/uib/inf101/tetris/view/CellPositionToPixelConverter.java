package no.uib.inf101.tetris.view;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;

public class CellPositionToPixelConverter {
  private Rectangle2D box;
  private GridDimension gd;
  private double margin;

  public CellPositionToPixelConverter(Rectangle2D box, GridDimension gd, double margin) {
    this.box = box;
    this.gd = gd;
    this.margin = margin;
  }

  /**
   * Return rectangle shape for given cell position. If roundCorner Boolean is
   * true, corners of shpe are rounded.
   * 
   * @param cp
   * @param roundCorner
   * @return shape for given cell position.
   */
  public Shape getBoundsForCell(CellPosition cp, Boolean roundCorner) {
    double row = cp.row();
    double col = cp.col();
    double boxTopY = this.box.getY();
    double boxTopX = this.box.getX();
    double boxWidth = this.box.getWidth();
    double boxHeight = this.box.getHeight();
    double rows = gd.rows();
    double cols = gd.cols();
    double margin = this.margin;

    double cellHeight = (boxHeight - margin * (rows + 1)) / rows;
    double cellWidht = (boxWidth - margin * (cols + 1)) / cols;

    double cellX = boxTopX + col * cellWidht + margin * (col + 1);
    double cellY = boxTopY + row * cellHeight + margin * (row + 1);

    if (roundCorner) {
      RoundRectangle2D roundRec = new RoundRectangle2D.Double(cellX, cellY, cellWidht, cellHeight, margin * 2,
          margin * 2);
      return roundRec;
    }

    return new Rectangle2D.Double(cellX, cellY, cellWidht, cellHeight);
  }
}
