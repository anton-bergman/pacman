package se.liu.antbe028.pacman.objects;

import se.liu.antbe028.pacman.Direction;
import se.liu.antbe028.pacman.GhostColor;

import java.awt.*;
import javax.swing.ImageIcon;

import java.util.*;
import java.util.List;
import java.util.Map.Entry;

/** Ghost is a subclass of the Entity-class and contains all the attributes
 * that are specific to the ghost-entity such as, color, images, animation,
 * the Ghost-AI and more.
 */

public class Ghost extends Entity
{

    private GhostColor color;
    private boolean activated = false;

    private int targetIndex;

    private List<Image> ghostBlueImagesUp = new ArrayList<>();
    private List<Image> ghostBlueImagesDown = new ArrayList<>();
    private List<Image> ghostBlueImagesLeft = new ArrayList<>();
    private List<Image> ghostBlueImagesRight = new ArrayList<>();

    private List<Image> ghostOrangeImagesUp = new ArrayList<>();
    private List<Image> ghostOrangeImagesDown = new ArrayList<>();
    private List<Image> ghostOrangeImagesLeft = new ArrayList<>();
    private List<Image> ghostOrangeImagesRight = new ArrayList<>();

    private List<Image> ghostPurpleImagesUp = new ArrayList<>();
    private List<Image> ghostPurpleImagesDown = new ArrayList<>();
    private List<Image> ghostPurpleImagesLeft = new ArrayList<>();
    private List<Image> ghostPurpleImagesRight = new ArrayList<>();

    private List<Image> ghostRedImagesUp = new ArrayList<>();
    private List<Image> ghostRedImagesDown = new ArrayList<>();
    private List<Image> ghostRedImagesLeft = new ArrayList<>();
    private List<Image> ghostRedImagesRight = new ArrayList<>();

    private int animCounter = 0;
    private int showImageIndex = 0;
    private static final int ANIM_LIMIT = 99;

    private static final int DOT = 16;
    private static final int FREE = 0;


    private Direction newDirection = Direction.NONE;

    private int nextStep;

    public Ghost(Board board, GhostColor color, int startX, int startY) {
        super(board, startX, startY);
        this.color = color;

        loadImages();
        setCoordinates(startX, startY);
        final int ghostSpeed = 3;
        setCurrentSpeed(ghostSpeed);
        final int sizeOfGhost = 20; // 20 pixels a side
        setSizeOfEntity(sizeOfGhost);
    }

    public void loadImages() {
        // Loads all images and adds them to the correct list

        Image img1BlueUp = new ImageIcon("resources/images/Ghosts/Blue/Up/G1.png").getImage();
        Image img2BlueUp = new ImageIcon("resources/images/Ghosts/Blue/Up/G2.png").getImage();
        ghostBlueImagesUp.add(img1BlueUp);
        ghostBlueImagesUp.add(img2BlueUp);

        Image img1BlueDown = new ImageIcon("resources/images/Ghosts/Blue/Down/G1.png").getImage();
        Image img2BlueDown = new ImageIcon("resources/images/Ghosts/Blue/Down/G2.png").getImage();
        ghostBlueImagesDown.add(img1BlueDown);
        ghostBlueImagesDown.add(img2BlueDown);

        Image img1BlueLeft = new ImageIcon("resources/images/Ghosts/Blue/Left/G1.png").getImage();
        Image img2BlueLeft = new ImageIcon("resources/images/Ghosts/Blue/Left/G2.png").getImage();
        ghostBlueImagesLeft.add(img1BlueLeft);
        ghostBlueImagesLeft.add(img2BlueLeft);

        Image img1BlueRight = new ImageIcon("resources/images/Ghosts/Blue/Right/G1.png").getImage();
        Image img2BlueRight = new ImageIcon("resources/images/Ghosts/Blue/Right/G2.png").getImage();
        ghostBlueImagesRight.add(img1BlueRight);
        ghostBlueImagesRight.add(img2BlueRight);

        Image img1OrangeUp = new ImageIcon("resources/images/Ghosts/Orange/Up/G1.png").getImage();
        Image img2OrangeUp = new ImageIcon("resources/images/Ghosts/Orange/Up/G2.png").getImage();
        ghostOrangeImagesUp.add(img1OrangeUp);
        ghostOrangeImagesUp.add(img2OrangeUp);

        Image img1OrangeDown = new ImageIcon("resources/images/Ghosts/Orange/Down/G1.png").getImage();
        Image img2OrangeDown = new ImageIcon("resources/images/Ghosts/Orange/Down/G2.png").getImage();
        ghostOrangeImagesDown.add(img1OrangeDown);
        ghostOrangeImagesDown.add(img2OrangeDown);

        Image img1OrangeLeft = new ImageIcon("resources/images/Ghosts/Orange/Left/G1.png").getImage();
        Image img2OrangeLeft  = new ImageIcon("resources/images/Ghosts/Orange/Left/G2.png").getImage();
        ghostOrangeImagesLeft.add(img1OrangeLeft);
        ghostOrangeImagesLeft.add(img2OrangeLeft);

        Image img1OrangeRight = new ImageIcon("resources/images/Ghosts/Orange/Right/G1.png").getImage();
        Image img2OrangeRight = new ImageIcon("resources/images/Ghosts/Orange/Right/G2.png").getImage();
        ghostOrangeImagesRight.add(img1OrangeRight);
        ghostOrangeImagesRight.add(img2OrangeRight);

        Image img1PurpleUp = new ImageIcon("resources/images/Ghosts/Purple/Up/G1.png").getImage();
        Image img2PurpleUp = new ImageIcon("resources/images/Ghosts/Purple/Up/G2.png").getImage();
        ghostPurpleImagesUp.add(img1PurpleUp);
        ghostPurpleImagesUp.add(img2PurpleUp);

        Image img1PurpleDown = new ImageIcon("resources/images/Ghosts/Purple/Down/G1.png").getImage();
        Image img2PurpleDown = new ImageIcon("resources/images/Ghosts/Purple/Down/G2.png").getImage();
        ghostPurpleImagesDown.add(img1PurpleDown);
        ghostPurpleImagesDown.add(img2PurpleDown);

        Image img1PurpleLeft = new ImageIcon("resources/images/Ghosts/Purple/Left/G1.png").getImage();
        Image img2PurpleLeft = new ImageIcon("resources/images/Ghosts/Purple/Left/G2.png").getImage();
        ghostPurpleImagesLeft.add(img1PurpleLeft);
        ghostPurpleImagesLeft.add(img2PurpleLeft);

        Image img1PurpleRight = new ImageIcon("resources/images/Ghosts/Purple/Right/G1.png").getImage();
        Image img2PurpleRight = new ImageIcon("resources/images/Ghosts/Purple/Right/G2.png").getImage();
        ghostPurpleImagesRight.add(img1PurpleRight);
        ghostPurpleImagesRight.add(img2PurpleRight);

        Image img1RedUp = new ImageIcon("resources/images/Ghosts/Red/Up/G1.png").getImage();
        Image img2RedUp = new ImageIcon("resources/images/Ghosts/Red/Up/G2.png").getImage();
        ghostRedImagesUp.add(img1RedUp);
        ghostRedImagesUp.add(img2RedUp);

        Image img1RedDown = new ImageIcon("resources/images/Ghosts/Red/Down/G1.png").getImage();
        Image img2RedDown = new ImageIcon("resources/images/Ghosts/Red/Down/G2.png").getImage();
        ghostRedImagesDown.add(img1RedDown);
        ghostRedImagesDown.add(img2RedDown);

        Image img1RedLeft = new ImageIcon("resources/images/Ghosts/Red/Left/G1.png").getImage();
        Image img2RedLeft = new ImageIcon("resources/images/Ghosts/Red/Left/G2.png").getImage();
        ghostRedImagesLeft.add(img1RedLeft);
        ghostRedImagesLeft.add(img2RedLeft);

        Image img1RedRight = new ImageIcon("resources/images/Ghosts/Red/Right/G1.png").getImage();
        Image img2RedRight = new ImageIcon("resources/images/Ghosts/Red/Right/G2.png").getImage();
        ghostRedImagesRight.add(img1RedRight);
        ghostRedImagesRight.add(img2RedRight);
    }

    public Image getImage() {
        // Returns the correct image depending on ghost color and animation
        List<Image> ghostImagesUp;
        List<Image> ghostImagesDown;
        List<Image> ghostImagesLeft;
        List<Image> ghostImagesRight;

        switch (color) {
            case BLUE:
                ghostImagesUp = ghostBlueImagesUp;
                ghostImagesDown = ghostBlueImagesDown;
                ghostImagesLeft = ghostBlueImagesLeft;
                ghostImagesRight = ghostBlueImagesRight;
                break;

            case ORANGE:
                ghostImagesUp = ghostOrangeImagesUp;
                ghostImagesDown = ghostOrangeImagesDown;
                ghostImagesLeft = ghostOrangeImagesLeft;
                ghostImagesRight = ghostOrangeImagesRight;
                break;

            case RED:
                ghostImagesUp = ghostRedImagesUp;
                ghostImagesDown = ghostRedImagesDown;
                ghostImagesLeft = ghostRedImagesLeft;
                ghostImagesRight = ghostRedImagesRight;
                break;

            case PURPLE:
                ghostImagesUp = ghostPurpleImagesUp;
                ghostImagesDown = ghostPurpleImagesDown;
                ghostImagesLeft = ghostPurpleImagesLeft;
                ghostImagesRight = ghostPurpleImagesRight;
                break;
            default:
                throw new IllegalArgumentException("Error: Could not get GhostImageList");

        }
        List<Image> ghostImages;

        if (currentDir == Direction.UP) {
            ghostImages = ghostImagesUp;
        }
        else if (currentDir == Direction.DOWN) {
            ghostImages = ghostImagesDown;
        }
        else if (currentDir == Direction.LEFT) {
            ghostImages = ghostImagesLeft;
        }
        else if (currentDir == Direction.RIGHT) {
            ghostImages = ghostImagesRight;
        }
        else {
            switch (oldDirection) {
                case UP:
                    ghostImages = ghostImagesUp;
                    break;
                case DOWN:
                    ghostImages = ghostImagesDown;
                    break;
                case LEFT:
                    ghostImages = ghostImagesLeft;
                    break;
                case RIGHT:
                    ghostImages = ghostImagesRight;
                    break;
                default:
                    throw new IllegalArgumentException("Error: Could not get Ghost Image");
            }
        }

        if (showImageIndex == 0) {
            return ghostImages.get(0);
        }
        else if (showImageIndex == 1) {
            return ghostImages.get(1);
        }
        else {
            return ghostImages.get(1);
        }
    }

    public void ghostAnimCounter(){
        if (animCounter == ANIM_LIMIT){
            animCounter = 0;
        }
        if (animCounter % 2 == 0){
            showImageIndex++;
        }
        else{
            showImageIndex = 0;
        }
        animCounter++;
    }

    public GhostColor getColor() {
        return color;
    }

    public void setActivated(boolean activation) {
        activated = activation;
    }
    

    public EnumMap<Direction, Integer> getNeighbours(int index) {
        // Returns a EnumMap with neighbouring indexes calculated from the current position
        // Kan endast köras när man är i mitten av en square
        EnumMap<Direction, Integer> neighbours = new EnumMap<>(Direction.class);

        // UP
        int indexAbove = index - board.getNumOfBlocks();
        short squareAbove = board.getMapSquare(indexAbove);
        // DOWN
        int indexBelow = index + board.getNumOfBlocks();
        short squareBelow = board.getMapSquare(indexBelow);
        // LEFT
        int indexLeft = index - 1;
        short squareLeft = board.getMapSquare(indexLeft);
        // RIGHT
        int indexRight = index + 1;
        short squareRight = board.getMapSquare(indexRight);

        if (squareAbove == DOT || squareAbove == FREE) {
            neighbours.put(Direction.UP, indexAbove);
        }
        if (squareBelow == DOT || squareBelow == FREE) {
            neighbours.put(Direction.DOWN, indexBelow);
        }
        if (squareLeft == DOT || squareLeft == FREE) {
            neighbours.put(Direction.LEFT, indexLeft);
        }
        if (squareRight == DOT || squareRight == FREE) {
            neighbours.put(Direction.RIGHT, indexRight);
        }
        return neighbours;
    }

    public void setTargetIndex(int pacmanIndex) {
        targetIndex = pacmanIndex;
    }

    public List<Map<Direction, Integer>> findShortestPath() {
        // Calculates the shortest path from the ghost to pacman

        // Get the current position
        int startNode = getIndex();

        // Create queue and add the start position
        Deque<Integer> queue = new LinkedList<>();
        queue.add(startNode);

        // Create a set of visited nodes.
        Set<Integer> visited = new HashSet<>();

        // Create a map of all tested paths
        Map<Integer, List<Map<Direction, Integer>>> paths = new HashMap<>();

        // Create a list of tuples containing direction and map index
        List<Map<Direction, Integer>> directionTuples = new ArrayList<>();

        // Create a start EnumMap containing direction and map index
        Map<Direction, Integer> startTuple = new EnumMap<>(Direction.class);

        // Create a Map with direction and map index of the neighbours of the start position
        Map<Direction, Integer> startNeighbours = getNeighbours(startNode);
        Direction keyToStartNode = Direction.NONE;

        // Find the direction to the start tuple
        for (Entry<Direction, Integer> entry : startNeighbours.entrySet()) {
            if (entry.getValue() == startNode) {
                keyToStartNode = entry.getKey();
            }
        }

        startTuple.put(keyToStartNode, startNode);
        directionTuples.add(startTuple);
        paths.put(startNode, directionTuples);

        // Runs while the queue is not empty
        while (!queue.isEmpty()) {
            // The position we're currently looking at
            int node = queue.removeFirst();

            if (node == targetIndex) {
                // if the node equals the target index return the path to the node
                List<Map<Direction, Integer>> path = paths.get(node);
                List<Map<Direction, Integer>> result = new ArrayList<>(path);
                result.remove(0);
                return result;
            }

            // For-loop looks at all the neighbouring positions
            Map<Direction, Integer> neighbours = getNeighbours(node);
            for (int neighbour : neighbours.values()) {
                if (!visited.contains(neighbour)) {
                    // If the neighbour ha not been visited, add to queue and visited
                    queue.add(neighbour);
                    visited.add(neighbour);

                    // Create a copy of the path to the node
                    List<Map<Direction, Integer>> path = paths.get(node);
                    List<Map<Direction, Integer>> pathToNodeCopy = new ArrayList<>(path);

                    Direction keyToNode = Direction.NONE;
                    for (Entry<Direction, Integer> entry : neighbours.entrySet()) {
                        if (entry.getValue() == neighbour) {
                            keyToNode = entry.getKey();
                        }
                    }
                    Map<Direction, Integer> neighbourTuple = new EnumMap<>(Direction.class);
                    neighbourTuple.put(keyToNode, neighbour);

                    pathToNodeCopy.add(neighbourTuple);
                    
                    paths.put(neighbour, pathToNodeCopy);
                }
            }
        }
        System.out.println("Ingen väg hittades");
        List<Map<Direction, Integer>> result = new ArrayList<>();
        return result;
    }


    public void setNextMove() {
        // Sets the next coordinate to move to
        List<Map<Direction, Integer>> shortestPath = findShortestPath();
        if (!shortestPath.isEmpty()) {
            Map<Direction, Integer> step = shortestPath.get(0);

            if (step.containsKey(Direction.UP)) {
                newDirection = Direction.UP;
            }
            else if (step.containsKey(Direction.DOWN)) {
                newDirection = Direction.DOWN;
            }
            else if (step.containsKey(Direction.LEFT)) {
                newDirection = Direction.LEFT;
            }
            else if (step.containsKey(Direction.RIGHT)) {
                newDirection = Direction.RIGHT;
            }
            nextStep = step.get(newDirection);
        }
    }


    public void moveToNextStep() {
        // Moves to the next step
        if (getIndex() != nextStep) {
            moveEntity(currentDir);
        }
    }


    public void tick() {

        if (activated) {
            ghostAnimCounter();
            setNextMove();
            if (!hasCollision(currentDir)) {
                if (isInMiddleOfSquare()) {
                    if (currentDir != newDirection && hasCollision(newDirection)) {
                        // If we have a change in direction, test so that the new direction causes a collision.
                        newDirection = Direction.NONE;
                    }
                    currentDir = newDirection;
                    newDirection = Direction.NONE; // Reset the newDirection variable
                }
                moveToNextStep();
            }
        }
    }
}
