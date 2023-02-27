package se.liu.antbe028.pacman.objects;

import se.liu.antbe028.pacman.BoardListener;

import java.util.*;

/**
 *  Board is the class representing the pacman maze. The class also contains
 * methods that act on the board, such as removing dots when eated etc.
 */

public class Board {

    private static final int BLOCK_SIZE = 24;
    private static final int NUM_BLOCKS = 20;

    private static final int DOT = 16;

    private List<BoardListener> boardListeners = new ArrayList<>();

    /*More information about the map is found in the report, see "6.2.6 Board"*/

    private final short[] map = {

        3 ,10,10,10,10,10,10,10,10, 2,10,10,10,10,10,10,10,10, 6 ,0,
        5 ,16,16,16,16,16,16,16,16, 5,16,16,16,16,16,16,16,16, 5 ,0,
        5 ,16,3 ,2 , 6,16,3 ,6 ,16, 5,16,3 ,6 ,16,3 , 2, 6,16, 5 ,0,
        5 ,16,1 , 0, 4,16,9 ,12,16,13,16,9 ,12,16, 1, 0, 4,16, 5 ,0,
        5 ,16, 9, 8,12,16,16,16,16,16,16,16,16,16, 9, 8 ,12,16,5 ,0,
        5 ,16,16,16,16,16, 7,16,11, 2,14,16, 7,16,16,16,16,16, 5 ,0,
        5 ,16,11,10,14,16, 5,16,16,5 ,16,16, 5,16,11,10,14,16, 5 ,0,
        5 ,16,16,16,16,16, 9,14,16,13,16,11,12,16,16,16,16,16, 5 ,0,
        1 ,10,10,10,14, 0, 0, 0, 0, 0, 0, 0, 0, 0,11,10,10,10, 4 ,0,
        5 , 0, 0, 0, 0, 0, 3,10,14, 0,11,10, 6, 0, 0, 0, 0, 0, 5 ,0,
        5 , 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 5 ,0,
        1 ,10,10,10,14, 0, 9,10,10,10,10,10,12, 0,11,10,10,10, 4 ,0,
        5 ,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16, 5 ,0,
        5 ,16,11,10, 6,16, 7,16,11,10,14,16, 7,16, 3,10,14,16, 5 ,0,
        5 ,16,16,16, 5,16, 5,16,16, 0,16,16, 5,16, 5,16,16,16, 5 ,0,
        1 ,10,14,16,13,16, 5,16,11, 2,14,16, 5,16,13,16,11,10, 4 ,0,
        5 ,16,16,16,16,16, 5,16,16, 5,16,16, 5,16,16,16,16,16, 5 ,0,
        5 ,16,11,10,10,10, 8,14,16,13,16,11, 8,10,10,10,14,16, 5 ,0,
        5 ,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16, 5 ,0,
        9 ,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,12 ,0
    };

    private short[] mapData;

    public Board() {
        initMap();
    }

    public short getMapSquare(int index) {
        return mapData[index];
    }

    public int getLength() {
        return NUM_BLOCKS*BLOCK_SIZE;
    }

    public int getBlockSize() {
        return BLOCK_SIZE;
    }

    public int getNumOfBlocks() {
        double length = map.length;
        int heightWidth = (int) Math.sqrt(length);
        return heightWidth;
    }

    public short[] getMapData() {
        return mapData;
    }

    public void setMapData(int index, int data) {
        short newData = (short) data;
        mapData[index] = newData;
    }

    private void initMap() {
        mapData = new short[NUM_BLOCKS*NUM_BLOCKS];

        for (int i = 0; i < NUM_BLOCKS * NUM_BLOCKS; i++) {
            mapData[i] = map[i];
        }
    }

    public boolean hasEatenAllDots() {
        for (int i = 0; i < map.length; i++) {
            if (map[i] == DOT && mapData[i] != 0) {
                return false;
            }
            
        }
        return true;
    }

    public void resetMap() {
        for (int i = 0; i < map.length; i++) {
            mapData[i] = map[i];
        }
    }

    public void addBoardChanged(BoardListener bl) {
        // Adds a BoardListner to the list of BoardListners
        boardListeners.add(bl);
    }

    private void notifyListeners() {
        // Notifies every BoardListner in the list of BoardListners
        for (BoardListener listener : boardListeners) {
            listener.boardChanged();
        }
    }

    public void tick() {

        notifyListeners();
    }
}

