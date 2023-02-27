package se.liu.antbe028.pacman.action;

import javax.swing.*;
import java.awt.event.ActionEvent;

/** QuitAction is a subclass to AbstractAction and dictates what
 * should happen when an action to quit the program has been detected.
 */

public class QuitAction extends AbstractAction {

    private JFrame frame;
    private int exitCode;

    public QuitAction(JFrame frame, int exitCode) {
        this.frame = frame;
    }

    @Override public void actionPerformed(final ActionEvent e) {
        frame.dispose();
		System.exit(exitCode);
    }

    @Override public final QuitAction clone() throws CloneNotSupportedException {
        return (QuitAction) super.clone();
    }
    
}
