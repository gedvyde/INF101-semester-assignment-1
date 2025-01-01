package no.uib.inf101.grid;

/**
 * A Gridcell consists of a Cellposition and a generic type parameter.
 *
 * @param pos   of type Cellposition
 * @param value of generic type <E>
 */
public record GridCell<E>(CellPosition pos, E value) {
}