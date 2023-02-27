package se.liu.antbe028.pacman.action;

import se.liu.antbe028.pacman.Direction;
import se.liu.antbe028.pacman.objects.Pacman;

import javax.swing.*;

import java.awt.event.ActionEvent;

/** The MoveAction class is a subclass to AbstractAction and describes
 * what the game should do when a key to change direction has been pressed.
 */

public class MoveAction extends AbstractAction {
    
    private Direction direction;
    private Pacman pacman;

    public MoveAction(Pacman pacman, Direction direction) {
        this.pacman = pacman;
        this.direction = direction;
    }

    @Override public void actionPerformed(final ActionEvent e) {
        pacman.keyPressed(direction);
    }

    @Override public final MoveAction clone() throws CloneNotSupportedException {
        return (MoveAction) super.clone();
    }

}
