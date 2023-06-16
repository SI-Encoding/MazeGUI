package ca.java.mazegui;

import ca.java.mazegui.UI.Canvas;
import ca.java.mazegui.UI.CanvasIcon;
import ca.java.mazegui.maze.Maze;

import javax.swing.*;

/**
 * This class constructs the GUI based off of the model classes Maze and Cell. It outputs a maze coloured black and white.
 * Black - walls
 * White - open spaces
 *
 * @author SI-Encoding
 */

public class MazeGUI {

    // Default size of the panels
    private static final int SIZE_X = 20;
    private static final int SIZE_Y = 16;


    public static void main(String[] args) {
        // Sample function showing use of Canvas, which will be needed when writing your Shapes.
        Canvas canvas = new Canvas(SIZE_X, SIZE_Y);

        Maze maze = new Maze(SIZE_Y, SIZE_X, canvas);


        // You should understand the rest of this function.
        // - the JFrame is creating the graphical UI
        // - the JLabel and CanvasIcon are what PicturePanel will do for you to display your shape
        // - other calls setup the GUI's window's behaviour
        JFrame frame = new JFrame();
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.add(new JLabel(new CanvasIcon(canvas)));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


}
