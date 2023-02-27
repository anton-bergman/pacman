package se.liu.antbe028.pacman.action;

import se.liu.antbe028.pacman.objects.Pacman;

import javax.swing.*;
import java.awt.event.ActionEvent;

/** StartAction is a subclass of AbstractAction and describes what happends
 * when an action to start or restart the game has been taken.
 */

public class StartAction extends AbstractAction {

    private Pacman pacman;
    private Timer gameTimer;

    public StartAction(Pacman pacman, Timer gameTimer) {
        this.pacman = pacman;
        this.gameTimer = gameTimer;
    }

    @Override public void actionPerformed(final ActionEvent e) {
        if (pacman.getGameOver() == null) {
            // Game hasn't started yet
            gameTimer.start(); // Start the game
            pacman.setGameOver(false);
        }
        else if (pacman.getGameOver()) {
            // It's Game Over, restart the game
            gameTimer.restart();
            pacman.setGameOver(false);
        }
        else if (pacman.getLevelCompleted()) {
            // If the level is completed, restart if S is presset
            gameTimer.start();
            pacman.setLevelCompleted(false);
        }
    }

    @Override public final StartAction clone() throws CloneNotSupportedException {
        return (StartAction) super.clone();
    }
}
