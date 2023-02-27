package se.liu.antbe028.pacman.objects;

import se.liu.antbe028.pacman.Direction;

import java.awt.*;

/** The Entity class is the generell representation of an entity.
 * An entity is either Pacman or a Ghost.The class contains everything
 * that all entities have in common such as, coordinates, direction, hitbox,
 * speed and collision handler.
 */

public abstract class Entity {

    protected Board board;

    protected int xStartCoord;
    protected int yStartCoord;

    protected int xCoord;
    protected int yCoord;
    protected int currentSpeed;

    protected Direction currentDir = Direction.NONE;
    protected Direction oldDirection = Direction.RIGHT;

    protected int sizeOfEntity;
    protected Rectangle hitBox = new Rectangle(xCoord, yCoord, sizeOfEntity, sizeOfEntity);


    protected Entity(Board board, int startX, int startY) {
        this.board = board;
        this.xStartCoord = startX;
        this.yStartCoord = startY;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public Rectangle getHitbox() {
        return hitBox;
    }

    public void setCoordinates(int x, int y) {
        xCoord = x;
        yCoord = y;
    }

    public void setCurrentSpeed(int speed) {
        currentSpeed = speed;
    }

    public void setCurrentDir(Direction dir) {
        currentDir = dir;
    }

    public void setSizeOfEntity(int size) {
        sizeOfEntity = size;
    }

    public void respawn() {
        xCoord = xStartCoord;
        yCoord = yStartCoord;
        currentDir = Direction.NONE;
        oldDirection = Direction.RIGHT;
    }

    public boolean isInMiddleOfSquare() {
        int blockSize = board.getBlockSize();
        boolean isInMiddle = xCoord % blockSize == 0 && yCoord % blockSize == 0;
        return isInMiddle;

    }

    public int getIndex() {
        // Calculates the current index from the current coordinates
        double columnDouble = (double) xCoord / board.getBlockSize();
        int column = (int) Math.round(columnDouble);

        double rowsDownDouble = (double) yCoord / board.getBlockSize();
        int rowsDown = (int) Math.round(rowsDownDouble);

        int row = rowsDown * board.getNumOfBlocks();

        int currentIndex = row + column;

        return currentIndex;
    }

    public boolean hasCollision(Direction dir) {
        // Checks for an entitys collision with the maze

        int blockSize = board.getBlockSize();
        int numBlocks = board.getNumOfBlocks();

        if (isInMiddleOfSquare()) {
            // If pacman stands perfectly on one square
            // Calculates index in the map short
            int pos = xCoord / blockSize + numBlocks * (yCoord / blockSize);
            int nextIndex;
            
            switch (dir) {
                case UP:
                    nextIndex = pos - numBlocks;
                    break;
                case DOWN:
                    nextIndex = pos + numBlocks;
                    break;
                case LEFT:
                    nextIndex = pos - 1;
                    break;
                case RIGHT:
                    nextIndex = pos + 1;
                    break;
                case NONE:
                    return false;
                default:
                    throw new IllegalArgumentException("Something went wrong in the collision handler!");
            }
            

            if (!(0 <= nextIndex && nextIndex < numBlocks*numBlocks) || ((board.getMapSquare(nextIndex) != 0) && (board.getMapSquare(nextIndex) < 16))) {
                oldDirection = currentDir;
                currentDir = Direction.NONE;
                return true;
            }
        }
        return false;
    }

    public void updateHitBox() {
        hitBox.setBounds(xCoord, yCoord, sizeOfEntity, sizeOfEntity);
    }
    public void moveEntity(Direction currentDir) {
        // Moves the entity

        switch (currentDir) {
            case UP:
                yCoord -= currentSpeed;
                break;
            case DOWN:
                yCoord += currentSpeed;
                break;
            case LEFT:
                xCoord -= currentSpeed;
                break;
            case RIGHT:
                xCoord += currentSpeed;
                break;
            case NONE:
                // If direction is NONE do nothing.
                break;
            default:
                throw new IllegalArgumentException("Something went wrong when moving pacman!");

        }
        updateHitBox();
    }
    
}
