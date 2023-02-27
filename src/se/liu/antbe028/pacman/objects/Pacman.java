package se.liu.antbe028.pacman.objects;

import se.liu.antbe028.pacman.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

/** The Pacman-class is a subclass of the Entity-class and contains all the
 * attributes that are specific to the Pacman-entity such as, images, animation
 * lives, score, level-status, game-over status etc.
 */

public class Pacman extends Entity
{

    private List<Ghost> ghosts;

    private Image pacmanImgRight = null;
    private List<Image> pacmanImagesUp = new ArrayList<>();
    private List<Image> pacmanImagesDown = new ArrayList<>();
    private List<Image> pacmanImagesLeft = new ArrayList<>();
    private List<Image> pacmanImagesRight = new ArrayList<>();
    private int animCounter = 0;
    private int showImageIndex = 0;
    private static final int ANIM_LIMIT = 99;

    private static final int DOT = 16;
    private static final int POINTS = 10;

    private Direction newDirection = Direction.NONE;
    private boolean turnIfPossible = false;

    private int pacmanLives = 3;
    private Boolean gameOver = null;
    private boolean levelCompleted = false;

    private int score = 0;
    private int scoreOnThisLevel = 0; // Max score on a lvl is 1470, (147 dots)
    

    public Pacman(Board board, int startX, int startY, List<Ghost> ghosts) {
        super(board, startX, startY);
        this.ghosts = ghosts;

        loadImages();
        setCoordinates(startX, startY);
        final int pacmanSpeed = 4;
        setCurrentSpeed(pacmanSpeed);
        // 20 pixels a side
        final int sizeOfPacman = 20;
        setSizeOfEntity(sizeOfPacman);
    }

    public void loadImages() {
        // Loads all images and adds them to the correct list

        Image img1 = new ImageIcon("resources/images/Pacman/P0.png").getImage();
        pacmanImagesUp.add(img1);
        pacmanImagesDown.add(img1);
        pacmanImagesLeft.add(img1);
        pacmanImagesRight.add(img1);

        Image img2Up = new ImageIcon("resources/images/Pacman/Up/P1.png").getImage();
        Image img3Up = new ImageIcon("resources/images/Pacman/Up/P2.png").getImage();
        pacmanImagesUp.add(img2Up);
        pacmanImagesUp.add(img3Up);

        Image img2Down = new ImageIcon("resources/images/Pacman/Down/P1.png").getImage();
        Image img3Down = new ImageIcon("resources/images/Pacman/Down/P2.png").getImage();
        pacmanImagesDown.add(img2Down);
        pacmanImagesDown.add(img3Down);

        Image img2Left = new ImageIcon("resources/images/Pacman/Left/P1.png").getImage();
        Image img3Left = new ImageIcon("resources/images/Pacman/Left/P2.png").getImage();
        pacmanImagesLeft.add(img2Left);
        pacmanImagesLeft.add(img3Left);

        Image img2Right = new ImageIcon("resources/images/Pacman/Right/P1.png").getImage();
        Image img3Right = new ImageIcon("resources/images/Pacman/Right/P2.png").getImage();
        pacmanImagesRight.add(img2Right);
        pacmanImagesRight.add(img3Right);
        this.pacmanImgRight = img2Right;
    }

    public Image getPacmanImgRight() {
        return pacmanImgRight;
    }

    public Image getImage() {
        // Returns the correct image depending the animation counter
        List<Image> pacmanImages;

        if (currentDir == Direction.UP) {
            pacmanImages = pacmanImagesUp;
        }
        else if (currentDir == Direction.DOWN) {
            pacmanImages = pacmanImagesDown;
        }
        else if (currentDir == Direction.LEFT) {
            pacmanImages = pacmanImagesLeft;
        }
        else if (currentDir == Direction.RIGHT) {
            pacmanImages = pacmanImagesRight;
        }
        else {
            switch (oldDirection) {
                case UP:
                    pacmanImages = pacmanImagesUp;
                    break;
                case DOWN:
                    pacmanImages = pacmanImagesDown;
                    break;
                case LEFT:
                    pacmanImages = pacmanImagesLeft;
                    break;
                case RIGHT:
                case NONE:
                    pacmanImages = pacmanImagesRight;
                    break;
                default:
                    throw new IllegalArgumentException("Error: Could not get pacman Image");
            }
        }
        
        if (showImageIndex == 0) {
            return pacmanImages.get(0);
        }
        else if (showImageIndex == 1) {
            return pacmanImages.get(1);
        }
        else if (showImageIndex == 2) {
            return pacmanImages.get(2);
        }
        else {
            return pacmanImages.get(1);
        }
    }

    public void pacAnimCounter() {
        // Animation counter for the pacman animation
        if (animCounter == ANIM_LIMIT) {
            // So that the operations don't get to big
            animCounter = 0;
        }
        if (animCounter % 3 == 0 && animCounter != 0 && showImageIndex <= 3) {
            showImageIndex++;
        }
        if (showImageIndex > 3) {
            showImageIndex = 0;
        }
        animCounter++;
    }

    public int getPacmanLives() {
        return pacmanLives;
    }

    public int getScore() {
        return score;
    }

    public int getScoreOnThisLevel() {
        return scoreOnThisLevel;
    }

    public void lostALife() {
        // Removes a life from pacman
        pacmanLives--;
        if (pacmanLives <= 0) {
            gameOver = true;
        }
    }

    public Boolean getGameOver() {
        return gameOver;
    }

    public boolean getLevelCompleted() {
        return levelCompleted;
    }

    public void setLevelCompleted(boolean status) {
        levelCompleted = status;
    }

    public void setGameOver(Boolean status) {
        gameOver = status;
    }

    public void keyPressed(Direction newDir) {
        // A key to choose direction has been pressed
        this.newDirection = newDir;
        this.turnIfPossible = true;
    }

    public void eatDots() {
        // Removes the dots if pacman stands on one
        int currentIndex = getIndex();
        if (board.getMapSquare(currentIndex) == DOT) {
            score += POINTS;
            scoreOnThisLevel += POINTS;
            board.setMapData(currentIndex, 0); // Remove the dot from the map
        }
    }

    public void allDotsEaten() {
        //Checks if a level has been completed
        if (board.hasEatenAllDots()) {

            board.resetMap();
            setLevelCompleted(true);
            this.respawn();
            scoreOnThisLevel = 0;
            for (Ghost ghost : ghosts) {
                ghost.respawn();
                ghost.setActivated(false);
            }
        }
    }

    public void hitGhost() {
        // Checks if pacman has hit any ghosts
        for (Ghost ghost : ghosts) {
            Rectangle ghostHitBox = ghost.getHitbox();
            Rectangle pacmanHitBox = hitBox;

            if (ghostHitBox.intersects(pacmanHitBox)) {
                lostALife(); // Pacman loses a life

                for (Ghost g : ghosts) {
                    g.respawn();
                    g.setCurrentDir(Direction.NONE);
                }
    
                this.respawn(); // respawn pacman
                this.setCurrentDir(Direction.NONE);
            }
        }
    }

    public int getPacmanIndex() {
        // Returns the index of the target square för the ghosts
        int column = getXCoord() / board.getBlockSize();
        int rowsDown = getYCoord() / board.getBlockSize();
        int row = rowsDown * board.getNumOfBlocks();

        int pacmanIndex = row + column;

        return pacmanIndex;
    }

    public void resetIfGameOver() {
        // Resets the game if it's game over
        if (getGameOver()) {
            // If it is Game Over!
            this.respawn();
            for (Ghost ghost : ghosts) {
                ghost.respawn();
                ghost.setActivated(false);
            }

            pacmanLives = 3;
            score = 0;
            scoreOnThisLevel = 0;
            board.resetMap();
        }
    }

    public void tick() {

        int targetIndex = getPacmanIndex();
        for (Ghost ghost : ghosts) {
            ghost.setTargetIndex(targetIndex);
        }
        resetIfGameOver();
        allDotsEaten();
        hitGhost();
        pacAnimCounter();
        eatDots();

        if (!hasCollision(currentDir)) {
            if (turnIfPossible && isInMiddleOfSquare()) {
                // If the turn-key has been pressed and we're in the middle of a square

                if (currentDir != newDirection && hasCollision(newDirection)) {
                    // If we have a change in direction, test so that the new direction causes a collision.
                    newDirection = Direction.NONE;
                }
                currentDir = newDirection;

                newDirection = Direction.NONE;
                turnIfPossible = false;
            }
            moveEntity(currentDir);

        }
    }
}
