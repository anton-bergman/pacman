package se.liu.antbe028.pacman;

import java.util.*;

/** The enum class Direction, is a class that defines the different directions
 * an Entity can have.
 */

public enum Direction {

    LEFT, RIGHT, UP, DOWN, NONE;

    public static List<Direction> getDirections() {
        // Returns a list with att availible directions except NONE
        List<Direction> directions= new ArrayList<>();
        directions.add(LEFT);
        directions.add(RIGHT);
        directions.add(UP);
        directions.add(DOWN);

        return directions ;
    }
}
