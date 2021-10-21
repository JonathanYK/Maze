import java.awt.*;
import java.util.ArrayList;

public class mazePoint {
    private int x;
    private int y;
    private mazePoint parent;
    private boolean visited = false;

    public mazePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public mazePoint getPoint() {
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisited() {
        return visited;
    }


    // This method used for getting neighbors while generating the maze using DFS with the nearest neighbor only:
    public ArrayList<mazePoint> getAvailableNeighbors(int mazeSize, mazePoint[][] mazePoints) {

        ArrayList<mazePoint> availableNeighbors = new ArrayList<>();

        // North move:
        if(this.getX() != 0) {
            if(!mazePoints[x-1][y].isVisited())
                availableNeighbors.add(mazePoints[x-1][y]);
        }

        // South move:
        if(this.getX() < mazeSize-1) {
            if (!mazePoints[x + 1][y].isVisited())
                availableNeighbors.add(mazePoints[x + 1][y]);
        }

        // West move:
        if(this.getY() != 0) {
            if (!mazePoints[x][y - 1].isVisited())
                availableNeighbors.add(mazePoints[x][y - 1]);
    }

        // East move:
        if(this.getY() < mazeSize-1) {
            if (!mazePoints[x][y + 1].isVisited())
                availableNeighbors.add(mazePoints[x][y + 1]);
    }

        return availableNeighbors;
    }




    public mazePoint getParent() {
        return this.parent;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVisited(boolean val) {
        this.visited = val;
    }

    public void setParent(mazePoint p) {
        this.parent = p;
    }

    public boolean equals(mazePoint other) {
        return this.getX() == other.getX() && this.getY() == other.getY();
    }
}
