package se.liu.antbe028.pacman;

import se.liu.antbe028.pacman.action.MoveAction;
import se.liu.antbe028.pacman.action.QuitAction;
import se.liu.antbe028.pacman.action.StartAction;
import se.liu.antbe028.pacman.objects.Board;
import se.liu.antbe028.pacman.objects.Ghost;
import se.liu.antbe028.pacman.objects.Pacman;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/** PacmanComp is the games main Component-class which detects key-presses,
 * draws the map, draws pacman and draws the ghosts.
 */

public class PacmanComp extends JComponent implements BoardListener {
    
    private Board board;
    private Pacman pacman;
    private List<Ghost> ghosts;
    private Timer gameTimer;
    private Dimension windowDimension;

    private int screenSize;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);

    private static final int SCORE_BOARD_SIZE = 30;

    private static final String MOVE_LEFT = "move_left";
    private static final String MOVE_RIGHT = "move_right";
    private static final String MOVE_UP = "move_up";
    private static final String MOVE_DOWN = "move_down";
    private static final String QUIT = "quit";
    private static final String START_OR_RESTART = "start_or_restart";

    public PacmanComp(JFrame frame, Timer gameTimer, Board board, Pacman pacman, List<Ghost> ghosts) {
        this.board = board;
        this.pacman = pacman;
        this.ghosts = ghosts;
        this.gameTimer = gameTimer;

        int squareSide = board.getLength();
        Dimension windowDimension = new Dimension(squareSide, squareSide + SCORE_BOARD_SIZE + 30);
        this.windowDimension = windowDimension;

        screenSize = board.getNumOfBlocks() * board.getBlockSize();

        JComponent pane = frame.getRootPane();

        final InputMap in = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        in.put(KeyStroke.getKeyStroke("LEFT"), MOVE_LEFT);
        in.put(KeyStroke.getKeyStroke("RIGHT"), MOVE_RIGHT);

        in.put(KeyStroke.getKeyStroke("UP"), MOVE_UP);
        in.put(KeyStroke.getKeyStroke("DOWN"), MOVE_DOWN);

        in.put(KeyStroke.getKeyStroke("ESCAPE"), QUIT);

        in.put(KeyStroke.getKeyStroke("S"), START_OR_RESTART);

        final ActionMap act = pane.getActionMap();

        act.put(MOVE_LEFT, new MoveAction(pacman, Direction.LEFT));
        act.put(MOVE_RIGHT, new MoveAction(pacman, Direction.RIGHT));

        act.put(MOVE_UP, new MoveAction(pacman, Direction.UP));
        act.put(MOVE_DOWN, new MoveAction(pacman, Direction.DOWN));

        act.put(QUIT, new QuitAction(frame, 0));
        act.put(START_OR_RESTART, new StartAction(pacman, gameTimer));
    }

    public Dimension getWindowDimension() {
        return windowDimension;
    }

    public void boardChanged() {
        // Repaints the board
        this.repaint();
    }

    private void showIntroOrGameOverScreen(Graphics2D g2d) {
        // Displays an intro, game over or next level screen depending on the state of the game
        String s = null;
        if (pacman.getGameOver() == null){
            s = "Press S to play.";
        }
        else if (pacman.getGameOver()) {
            s = "Game Over! Press S to play again.";
            //gameTimer.stop();
        }
        else if (pacman.getLevelCompleted()) {
            // Level has been completed
            s = "Level completed! Press S to move on.";
            gameTimer.stop();
        }

        if (pacman.getGameOver() == null || pacman.getGameOver() || pacman.getLevelCompleted()) {
            g2d.setColor(new Color(0, 32, 48));
            g2d.fillRect(50, screenSize / 2 - 30, screenSize - 100, 50);
            g2d.setColor(Color.white);
            g2d.drawRect(50, screenSize / 2 - 30, screenSize - 100, 50);

            FontMetrics metr = this.getFontMetrics(smallFont);

            g2d.setColor(Color.white);
            g2d.setFont(smallFont);
            g2d.drawString(s, (screenSize - metr.stringWidth(s)) / 2, screenSize / 2);
        }
    }


    private void drawScore(Graphics2D g) {
        //  Draws the current score and lives

        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        String s = "Score: " + pacman.getScore();
        g.drawString(s, screenSize / 2 + 96, screenSize + 16);

        int pacmanLives = pacman.getPacmanLives();
        for (int i = 0; i < pacmanLives; i++) {
            g.drawImage(pacman.getPacmanImgRight(), i * 28 + 8, screenSize + 1, null);
        }
    }

    public void drawMap(Graphics2D g2d) {
        // Draws the map of the game

        Color mapColor = Color.BLUE;
        Color dotColor = new Color(192, 192, 0);

        int screenSize = board.getNumOfBlocks() * board.getBlockSize();

        short[] mapData = board.getMapData();

        short i = 0;
        for (int y = 0; y < screenSize; y += board.getBlockSize()) {
            for (int x = 0; x < screenSize; x += board.getBlockSize()) {

                g2d.setColor(mapColor);
                g2d.setStroke(new BasicStroke(2));

                if ((mapData[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + board.getBlockSize() - 1);
                }

                if ((mapData[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + board.getBlockSize() - 1, y);
                }

                if ((mapData[i] & 4) != 0) {
                    g2d.drawLine(x + board.getBlockSize() - 1, y, x + board.getBlockSize() - 1,
                            y + board.getBlockSize() - 1);
                }

                if ((mapData[i] & 8) != 0) {
                    g2d.drawLine(x, y + board.getBlockSize() - 1, x + board.getBlockSize() - 1,
                            y + board.getBlockSize() - 1);
                }

                if ((mapData[i] & 16) != 0) {
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }

                i++;
            }
        }
    }

    public void drawPacman(Graphics2D g2d) {
        // Draws pacman
        g2d.drawImage(pacman.getImage(), pacman.getXCoord(), pacman.getYCoord(), null);
    }

    public void drawGhosts(Graphics2D g2d) {
        // Draws the ghosts
        for (Ghost ghost : ghosts) {
            g2d.drawImage(ghost.getImage(), ghost.getXCoord(), ghost.getYCoord(), null);
        }
    }
    
    @Override protected void paintComponent(Graphics g) {
        // Component for painting objects
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, windowDimension.width, windowDimension.height);

        drawScore(g2d);
        drawMap(g2d);
        drawPacman(g2d);
        drawGhosts(g2d);
        showIntroOrGameOverScreen(g2d);

    }
}
