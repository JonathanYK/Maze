package model;

import java.awt.*;
import java.util.*;

public class Maze2d {

    String mazeName = null;
    int mazeSize;
    boolean[][] mazeStructure;
    Point entrance;
    Point exit;

    public Maze2d(String mazeName, int mazeSize, Point entrance, Point exit, boolean[][] mazeStructure) {
        this.mazeName = mazeName;
        this.mazeSize = mazeSize;
        this.entrance = entrance;
        this.exit = exit;
        this.mazeStructure = mazeStructure;
    }

    public Maze2d(int mazeSize) {

        // Initiating maze structure (default params are false):
        this.mazeStructure = new boolean[mazeSize][mazeSize];

        this.mazeSize = mazeSize;

        // Default entrance point is (0,0):
        this.entrance = new Point(0, 0);

        // Default exit is the bottom right corner:
        this.exit = new Point(mazeSize - 1, mazeSize - 1);
    }

    public void setMazeName(String newMazeName) {
        this.mazeName = newMazeName;
    }

    public boolean[][] getMazeStructure() { return this.mazeStructure; }

    public String getMazeName() {
        return this.mazeName;
    }

    public Point getEntrance() {
        return entrance;
    }

    public Point getExit() {
        return exit;
    }

    public void setCustomMaze(boolean[][] customMaze) throws Exception {

        if (customMaze[0].length != this.mazeSize || customMaze[1].length != this.mazeSize)
            throw new Exception("Maze structure isn't equal to the maze size!");
        this.mazeStructure = customMaze;
    }

    public int getMazeSize() {
        return mazeSize;
    }

    public void setPointVal(int x, int y, boolean val) {
        this.mazeStructure[x][y] = val;
    }

    public ArrayList<Point> getPossibleSteps(Point currPoint) {
        return getPossibleSteps(currPoint, false);
    }

    public ArrayList<Point> getPossibleSteps(Point currPoint, boolean ignoreWalls) {

        ArrayList<Point> possibleSteps = new ArrayList<>();

        // North move:
        if (currPoint.x != 0) {
            if (ignoreWalls || this.mazeStructure[currPoint.x - 1][currPoint.y])
                possibleSteps.add(new Point(currPoint.x - 1, currPoint.y));
        }

        // South move:
        if (currPoint.x < this.mazeSize - 1) {
            if (ignoreWalls || this.mazeStructure[currPoint.x + 1][currPoint.y])
                possibleSteps.add(new Point(currPoint.x + 1, currPoint.y));
        }

        // West move:
        if (currPoint.y != 0) {
            if (ignoreWalls || this.mazeStructure[currPoint.x][currPoint.y - 1])
                possibleSteps.add(new Point(currPoint.x, currPoint.y - 1));
        }

        // East move:
        if (currPoint.y < this.mazeSize - 1) {
            if (ignoreWalls || this.mazeStructure[currPoint.x][currPoint.y + 1])
                possibleSteps.add(new Point(currPoint.x, currPoint.y + 1));
        }
        return possibleSteps;
    }

    @Override
    public String toString() {
        final char PATH_CHAR = ' ';
        final char WALL_CHAR = '▓';
        final char BORDER_CHAR = '*';

        final StringBuilder sb = new StringBuilder();

        // Add upper maze wall:
        sb.append(String.valueOf(BORDER_CHAR).repeat(this.mazeSize + 2));
        sb.append('\n');

        for (int i = 0; i < this.mazeSize; i++) {
            sb.append(BORDER_CHAR);

            for (int j = 0; j < this.mazeSize; j++)
                sb.append(this.mazeStructure[i][j] ? PATH_CHAR : WALL_CHAR);
            sb.append(BORDER_CHAR);
            sb.append('\n');
        }

        // Add bottom maze wall:
        sb.append(String.valueOf(BORDER_CHAR).repeat(this.mazeSize + 2));
        sb.append('\n');

        return sb.toString();
    }
}





