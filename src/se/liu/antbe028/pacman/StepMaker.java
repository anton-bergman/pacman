package se.liu.antbe028.pacman;

import se.liu.antbe028.pacman.objects.Board;
import se.liu.antbe028.pacman.objects.Ghost;
import se.liu.antbe028.pacman.objects.Pacman;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.util.List;

/** StepMaker is a subclass of AbstractAction and is the class responsible
 * for moving the game one step forward after every tick.
 */

public class StepMaker extends AbstractAction{

    private Board board;
    private Pacman pacman;
    private List<Ghost> ghosts;
    private Ghost ghostBlue = null;
    private Ghost ghostOrange = null;
    private Ghost ghostRed = null;
    private Ghost ghostPurple = null;

    private static final int POINTS_300 = 300;
    private static final int POINTS_600 = 600;
    private static final int POINTS_900 = 900;

    public StepMaker(Board board, Pacman pacman, List<Ghost> ghosts) {
        this.board = board;
        this.pacman = pacman;
        this.ghosts = ghosts;

        for (Ghost ghost : ghosts) {
            if (ghost.getColor().equals(GhostColor.BLUE)) {
                this.ghostBlue = ghost;
            }
            else if (ghost.getColor().equals(GhostColor.ORANGE)) {
                this.ghostOrange = ghost;
            }
            else if (ghost.getColor().equals(GhostColor.RED)) {
                this.ghostRed = ghost;
            }
            else if (ghost.getColor().equals(GhostColor.PURPLE)) {
                this.ghostPurple = ghost;
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
	    // Move one step in the game!
        board.tick();
        pacman.tick();

        if (pacman.getScoreOnThisLevel() == 0) {
            ghostRed.setActivated(true);
        }
        else if (pacman.getScoreOnThisLevel() == POINTS_300) {
            ghostBlue.setActivated(true);
        }
        else if (pacman.getScoreOnThisLevel() == POINTS_600) {
            ghostOrange.setActivated(true);
        }
        else if (pacman.getScoreOnThisLevel() == POINTS_900) {
            ghostPurple.setActivated(true);
        }

        for (Ghost ghost : ghosts) {
            ghost.tick();
        }
    }

    @Override public final StepMaker clone() throws CloneNotSupportedException {
	return (StepMaker) super.clone();
    }
}
