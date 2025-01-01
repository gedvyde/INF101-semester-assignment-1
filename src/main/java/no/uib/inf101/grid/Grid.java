package no.uib.inf101.grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Grid<E> implements IGrid<E> {

  private int rows;
  private int cols;
  private List<List<GridCell<E>>> grid;

  public Grid(int rows, int cols) {
    this.cols = cols;
    this.rows = rows;
    initializeGrid(null);
  }

  public Grid(int rows, int cols, E element) {
    this.cols = cols;
    this.rows = rows;
    initializeGrid(element);
  }

  /**
   * Initializes grid with given element
   * 
   * @param element
   */
  private void initializeGrid(E element) {
    this.grid = new ArrayList<>();
    for (int r = 0; r < this.rows(); r++) {
      List<GridCell<E>> rowsAsList = new ArrayList<>();
      for (int c = 0; c < this.cols(); c++) {
        CellPosition pos = new CellPosition(r, c);
        GridCell<E> gridCell = new GridCell<E>(pos, element);
        rowsAsList.add(gridCell);
      }
      this.grid.add(rowsAsList);
    }
  }

  @Override
  public int rows() {
    return this.rows;
  }

  @Override
  public int cols() {
    return this.cols;
  }

  @Override
  public Iterator<GridCell<E>> iterator() {
    ArrayList<GridCell<E>> objekt = new ArrayList<>();
    for (List<GridCell<E>> row : this.grid) {
      objekt.addAll(row);
    }
    return objekt.iterator();
  }

  @Override
  public void set(CellPosition pos, E value) throws IndexOutOfBoundsException {
    if (!positionIsOnGrid(pos)) {
      throw new IndexOutOfBoundsException();
    }
    int row = pos.row();
    int col = pos.col();

    GridCell<E> gridCell = new GridCell<E>(pos, value);
    List<GridCell<E>> rowAsList = this.grid.get(row);
    rowAsList.set(col, gridCell);
  }

  @Override
  public E get(CellPosition pos) {
    if (!positionIsOnGrid(pos)) {
      throw new IndexOutOfBoundsException();
    }
    int row = pos.row();
    int col = pos.col();
    GridCell<E> gridCell = this.grid.get(row).get(col);
    return gridCell.value();
  }

  @Override
  public boolean positionIsOnGrid(CellPosition pos) {
    int row = pos.row();
    int col = pos.col();

    if (row < 0 || row >= this.rows()) {
      return false;
    }
    if (col < 0 || col >= this.cols()) {
      return false;
    }
    return true;
  }

}
