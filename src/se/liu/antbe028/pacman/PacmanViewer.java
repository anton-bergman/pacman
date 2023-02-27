package se.liu.antbe028.pacman;

import se.liu.antbe028.pacman.objects.Board;
import se.liu.antbe028.pacman.objects.Ghost;
import se.liu.antbe028.pacman.objects.Pacman;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** PacmanViewer is the class that creates the window in which the game is displayed.
 * This class also contains the initGame()-method that initializes the game. It creates
 * the instances of the ghosts, pacman, the board etc.
 */

public class PacmanViewer {

    private Board board = null;
    private Pacman pacman = null;
    private List<Ghost> ghosts = null;
    private Timer gameTimer = null;

    public void initGame() {
        // Initializes the game
        Board board = new Board();

        Ghost ghostBlue = new Ghost(board, GhostColor.RED, 216, 216);
        Ghost ghostOrange = new Ghost(board, GhostColor.BLUE, 216, 240);
        Ghost ghostRed = new Ghost(board, GhostColor.ORANGE, 192, 240);
        Ghost ghostPurple = new Ghost(board, GhostColor.PURPLE, 240, 240);
        List<Ghost> ghosts = new ArrayList<>();
        ghosts.add(ghostBlue);
        ghosts.add(ghostOrange);
        ghosts.add(ghostRed);
        ghosts.add(ghostPurple);

        Pacman pacman = new Pacman(board, 216, 336, ghosts);

        Action doOneStep = new StepMaker(board, pacman, ghosts);
        Timer gameTimer = new Timer(50, doOneStep);

        this.board = board;
        this.pacman = pacman;
        this.ghosts = ghosts;
        this.gameTimer = gameTimer;
    }
    
    public void show() {
        // Creates and displays the frame
        JFrame frame = new JFrame("Pacman");

        PacmanComp comp = new PacmanComp(frame, gameTimer, board, pacman, ghosts);
        board.addBoardChanged(comp);

        // Creates a new layout for the frame
        frame.setLayout(new BorderLayout());

        frame.add(comp, BorderLayout.CENTER);
        frame.setSize(comp.getWindowDimension());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Shows the frame
	    frame.setVisible(true);
    }
}
