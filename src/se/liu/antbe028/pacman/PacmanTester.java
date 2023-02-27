package se.liu.antbe028.pacman;

/** PacmanTester is the test-class which calls on the correct methods to
 * start and run the game.
 */

public class PacmanTester {

    public static void main(String[] args) {
        PacmanViewer viewer = new PacmanViewer();
        viewer.initGame();
        viewer.show();
    }
}
