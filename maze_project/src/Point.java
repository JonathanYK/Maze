import java.util.ArrayList;
import java.util.Collections;

public class Point {
    private int x;
    private int y;

    private boolean visited;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point getPoint() {
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

    public ArrayList<Point> getAvailableNeighbors(int mazeSize, Point[][] points) {

        ArrayList<Point> availableNeighbors = new ArrayList<>();

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



    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVisited() {
        this.visited = true;
    }

    public boolean equals(Point other) {
        return this.getX() == other.getX() && this.getY() == other.getY();
    }
}
