package ca.java.mazegui.maze;

import ca.java.mazegui.UI.Canvas;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This class generates a random maze using a random version of Depth First Search
 * implemented using a recursive backtracker using a stack.
 *
 * @author SI-Encoding
 */
public class Maze {

    private final int ROW;

    private final int COL;

    public Cell[][] cells;

    private final Random rand = new Random();

    private final int DECREMENT = 1;

    private final int INCREMENT = 1;

    private final int DECREMENT_BY_TWO = 2;

    private final int ROW_ONE = 1;

    private final int ROW_FOURTEEN = 14;

    private final int ROW_THIRTEEN = 13;

    private final int COL_ONE = 1;

    private final int COL_TWO = 2;

    private final int COL_FIFTEEN = 15;

    private final int COL_SIXTEEN = 16;

    private final int COL_SEVENTEEN = 17;

    private final int COL_EIGHTEEN = 18;

    private final Canvas canvas;

    public Maze(int row, int col, Canvas canvas) {
        this.ROW = row;
        this.COL = col;
        this.cells = new Cell[ROW][COL];
        this.canvas = canvas;
        construct();
        setUp();
    }

    // initialize with wall
    private void construct() {

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                cells[i][j] = new Cell(i, j, false, false);
                canvas.setCellColor(i, j, Color.BLACK);
            }
        }
    }

    private void setUp() {

        int[][] generators = {{1, 1}, {1, 18}, {14, 1}, {14, 18}};

        while (true) {

            for (int[] path : generators) {
                generate(path[0], path[1]);
            }

            check4Walls();

            check4InnerWalls();

            check4Spaces();

            if (check4InnerwallsAgain()) {
                reset();
            } else {

                break;
            }

        }
    }

    private void check4Walls() {

        //top
        for (int j = 1; j < COL - DECREMENT_BY_TWO; j++) {
            if (!cells[ROW_ONE][j].getOpen() && !cells[ROW_ONE][j + INCREMENT].getOpen()) {
                cells[ROW_ONE][j + INCREMENT].setOpen(true);
                canvas.setCellColor(j + 1, 1, Color.WHITE);
            }
        }

        //bottom
        for (int j = 1; j < COL - DECREMENT_BY_TWO; j++) {
            if (!cells[ROW_FOURTEEN][j].getOpen() && !cells[ROW_FOURTEEN][j + INCREMENT].getOpen()) {
                cells[ROW_FOURTEEN][j + INCREMENT].setOpen(true);
                canvas.setCellColor(j + 1, 14, Color.WHITE);
            }
            if (!cells[ROW_FOURTEEN][j].getOpen() && !cells[ROW_FOURTEEN][j + INCREMENT].getOpen()) {
                cells[ROW_FOURTEEN][j + INCREMENT].setOpen(true);
                canvas.setCellColor(j + 1, 14, Color.WHITE);
            }
        }

        //sides row
        for (int i = 1; i < ROW - DECREMENT_BY_TWO; i++) {
            if (!cells[i][INCREMENT].getOpen() && !cells[i + INCREMENT][COL_ONE].getOpen()) {
                cells[i + INCREMENT][COL_ONE].setOpen(true);
                canvas.setCellColor(1, i + 1, Color.WHITE);
            }

            if (!cells[i][COL_EIGHTEEN].getOpen() && !cells[i + INCREMENT][COL_EIGHTEEN].getOpen()) {
                cells[i + INCREMENT][COL_EIGHTEEN].setOpen(true);
                canvas.setCellColor(18, i + 1, Color.WHITE);
            }

        }
        // every square
        for (int i = 1; i < ROW - DECREMENT_BY_TWO; i++) {
            for (int j = 1; j < COL - DECREMENT_BY_TWO; j++) {
                if (!cells[i][j].getOpen() && !cells[i][j + INCREMENT].getOpen()) {
                    if (!cells[i - DECREMENT][j].getOpen() && !cells[i + INCREMENT][j].getOpen()) {
                        cells[i][j].setOpen(true);
                        canvas.setCellColor(j, i, Color.WHITE);
                    }
                    cells[i][j + INCREMENT].setOpen(true);
                    canvas.setCellColor(j + 1, i, Color.WHITE);
                }
            }
        }
    }

    private void check4InnerWalls() {
        for (int i = 1; i < ROW - DECREMENT_BY_TWO; i++) {
            for (int j = 1; j < COL - DECREMENT_BY_TWO; j++) {
                if (cells[i][j].getOpen() && !cells[i][j + INCREMENT].getOpen()
                        && !cells[i][j - DECREMENT].getOpen() && !cells[i + INCREMENT][j].getOpen()
                        && !cells[i - DECREMENT][j].getOpen()) {
                    cells[i][j + INCREMENT].setOpen(true);
                    canvas.setCellColor(j + 1, i, Color.WHITE);
                }
            }
        }
    }

    // bottom left corner got replaced by block
    private boolean check4InnerwallsAgain() {

        // check reverswe
        for (int i = ROW - DECREMENT_BY_TWO; i > 0; i--) {
            for (int j = COL - DECREMENT_BY_TWO; j > 0; j--) {
                if (cells[i][j].getOpen() && !cells[i][j + INCREMENT].getOpen()
                        && !cells[i][j - DECREMENT].getOpen()
                        && !cells[i + INCREMENT][j].getOpen() && !cells[i - DECREMENT][j].getOpen()) {
                    return true;
                } else if (cells[i][j].getOpen() && !cells[i][j + INCREMENT].getOpen()
                        && !cells[i][j - DECREMENT].getOpen() && !cells[i + INCREMENT][j].getOpen()
                        && !cells[i - DECREMENT_BY_TWO][j].getOpen()) {
                    return true;
                }
            }
        }

        // bottom left walled
        if (!cells[ROW_FOURTEEN][COL_ONE].getOpen() || cells[ROW_FOURTEEN][COL_ONE].getOpen() && cells[ROW_FOURTEEN][COL_TWO].getOpen()) {
            return true;
        }


        for (int i = 1; i < COL - DECREMENT_BY_TWO; i++) {
            if (!cells[ROW_FOURTEEN][i].getOpen() && !cells[ROW_THIRTEEN][i].getOpen()) {
                return true;
            }
        }

        // prevent large rows of walls
        for (int i = 1; i < ROW - DECREMENT_BY_TWO; i++) {

            if (!cells[i][COL_SIXTEEN].getOpen() && !cells[i + 1][COL_SIXTEEN].getOpen() && !cells[i + 2][COL_SIXTEEN].getOpen()) {
                return true;
            }
            if (!cells[i][COL_FIFTEEN].getOpen() && !cells[i + 1][COL_FIFTEEN].getOpen() && !cells[i + 2][COL_FIFTEEN].getOpen()) {
                return true;
            }

            if (!cells[i][COL_SEVENTEEN].getOpen() && !cells[i + 1][COL_SEVENTEEN].getOpen() && !cells[i + 2][COL_SEVENTEEN].getOpen()) {
                return true;
            }

        }

        return false;
    }

    private void check4Spaces() {


        for (int i = 1; i < ROW - DECREMENT_BY_TWO; i++) {
            for (int j = 1; j < COL - DECREMENT_BY_TWO; j++) {

                if (cells[i][j].getOpen() && cells[i][j + 1].getOpen()
                        && cells[i + 1][j].getOpen() && cells[i + 1][j + 1].getOpen()) {
                    cells[i + 1][j].setOpen(false);
                    canvas.setCellColor(j, i + 1, Color.BLACK);
                }

            }
        }
    }

    private void reset() {

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                cells[i][j].setOpen(false);
                cells[i][j].setVisited(false);
                canvas.setCellColor(j, i, Color.BLACK);
            }
        }
    }


    private void generate(int row, int col) {


        Stack<Cell> stack = new Stack<>();
        cells[row][col].setOpen(true);
        canvas.setCellColor(col, row, Color.WHITE);
        stack.push(cells[row][col]);

        while (!stack.isEmpty()) {

            Cell cell = stack.peek();

            row = cell.getRow();
            col = cell.getCol();

            List<Integer> neighbours = getNeighbours(row, col);

            if (!neighbours.isEmpty()) {


                int direct = neighbours.get(rand.nextInt(neighbours.size()));

                switch (direct) {
                    case 1 -> {
                        stack.push(cells[row - DECREMENT][col]);
                        cells[row - DECREMENT][col].setOpen(true);
                        canvas.setCellColor(col, row - 1, Color.WHITE);
                    }
                    case 2 -> {
                        stack.push(cells[row][col + INCREMENT]);
                        cells[row][col + INCREMENT].setOpen(true);
                        canvas.setCellColor(col + 1, row, Color.WHITE);
                    }
                    case 3 -> {
                        stack.push(cells[row + INCREMENT][col]);
                        cells[row + INCREMENT][col].setOpen(true);
                        canvas.setCellColor(col, row + 1, Color.WHITE);
                    }
                    case 0 -> {
                        stack.push(cells[row][col - DECREMENT]);
                        cells[row][col - DECREMENT].setOpen(true);
                        canvas.setCellColor(col - 1, row, Color.WHITE);
                    }
                }
            } else {
                stack.pop();
            }

        }


    }


    private List<Integer> getNeighbours(int row, int col) {

        List<Integer> neighbour = new ArrayList<>();

        if (col > 1 && !cells[row][col - DECREMENT].getOpen() && !cells[row][col - DECREMENT].getVisited()) {
            neighbour.add(0);
        }

        if (row > 1 && !cells[row - DECREMENT][col].getOpen() && !cells[row - DECREMENT][col].getVisited()) {
            neighbour.add(1);
        }

        if (col < COL - DECREMENT_BY_TWO && !cells[row][col + INCREMENT].getOpen() && !cells[row][col + INCREMENT].getVisited()) {
            neighbour.add(2);
        }

        if (row < ROW - DECREMENT_BY_TWO && !cells[row + INCREMENT][col].getOpen() && !cells[row + INCREMENT][col].getVisited()) {
            neighbour.add(3);
        }
        cells[row][col - DECREMENT].setVisited(true);
        cells[row - DECREMENT][col].setVisited(true);
        cells[row][col + INCREMENT].setVisited(true);
        cells[row + INCREMENT][col].setVisited(true);

        return neighbour;
    }

}