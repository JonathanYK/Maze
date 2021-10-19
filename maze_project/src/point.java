import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class point {
    private int x;
    private int y;
    private point parent;

    private boolean visited;

    public point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public point getPoint() {
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
    public ArrayList<point> getAvailableNeighbors(int mazeSize, point[][] points) {

        ArrayList<point> availableNeighbors = new ArrayList<>();

        // North move:
        if(this.getX() != 0) {
            if(!points[x-1][y].isVisited())
                availableNeighbors.add(points[x-1][y]);
        }

        // South move:
        if(this.getX() < mazeSize-1) {
            if (!points[x + 1][y].isVisited())
                availableNeighbors.add(points[x + 1][y]);
        }

        // West move:
        if(this.getY() != 0) {
            if (!points[x][y - 1].isVisited())
                availableNeighbors.add(points[x][y - 1]);
    }

        // East move:
        if(this.getY() < mazeSize-1) {
            if (!points[x][y + 1].isVisited())
                availableNeighbors.add(points[x][y + 1]);
    }

        return availableNeighbors;
    }




    public point getParent() {
        return this.parent;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVisited() {
        this.visited = true;
    }

    public void setParent(point p) {
        this.parent = p;
    }

    public boolean equals(point other) {
        return this.getX() == other.getX() && this.getY() == other.getY();
    }
}
