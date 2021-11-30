import java.awt.*;
import java.util.*;

public class maze2d {

    int mazeSize;
    boolean[][] mazeStructure;
    Point entrance;
    Point exit;

    public maze2d(int mazeSize, Point entrance, Point exit, boolean[][] mazeStructure) {

        this.mazeSize = mazeSize;
        this.entrance = entrance;
        this.exit = exit;
        this.mazeStructure = mazeStructure;
    }


    public maze2d(int mazeSize) {

        // initiating maze structure (default params are false):
        this.mazeStructure = new boolean[mazeSize][mazeSize];

        this.mazeSize = mazeSize;

        // default entrance point is (0,0):
        this.entrance = new Point(0, 0);

        // default exit is the bottom right corner:
        this.exit = new Point(mazeSize - 1, mazeSize - 1);
    }

    public Point getEntrance() {
        return entrance;
    }

    public Point getExit() {
        return exit;
    }


    public void setCustomMaze(boolean[][] customMaze) {
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

        // add upper maze wall:
        sb.append(String.valueOf(BORDER_CHAR).repeat(this.mazeSize + 2));
        sb.append('\n');

        for (int i = 0; i < this.mazeSize; i++) {
            sb.append(BORDER_CHAR);

            for (int j = 0; j < this.mazeSize; j++)
                sb.append(this.mazeStructure[i][j] ? PATH_CHAR : WALL_CHAR);
            sb.append(BORDER_CHAR);
            sb.append('\n');
        }

        // add bottom maze wall:
        sb.append(String.valueOf(BORDER_CHAR).repeat(this.mazeSize + 2));
        sb.append('\n');

        return sb.toString();
    }
}





