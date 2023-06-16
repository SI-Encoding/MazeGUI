package ca.java.mazegui.maze;


/**
 * This class stores the contents inside of the array, whether it is a wall or open space.
 * It aids in the Canvas class in informing which cell is colored appropriately.
 * These cells serve us the basis of a maze.
 *
 * @author SI-Encoding
 */
public class Cell {

    private boolean open;

    private boolean visited;

    private final int ROW;

    private final int COL;


    public Cell(int row, int col, boolean open, boolean visited) {

        this.ROW = row;
        this.COL = col;
        this.open = open;
        this.visited = visited;
    }

    public int getRow() {
        return ROW;
    }

    public int getCol() {
        return COL;
    }

    public boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {

        this.open = open;
    }

    public boolean getVisited() {
        return visited;
    }


    public void setVisited(boolean visited) {
        this.visited = visited;
    }

}
