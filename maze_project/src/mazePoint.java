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

    public mazePoint getPointOnMazePoints(mazePoint[][] mazePoints) {
        return mazePoints[this.getX()][this.getY()];
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


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\nmazePoint: [");
        sb.append(this.getX());
        sb.append("], [");
        sb.append(this.getY());
        sb.append("]");

        return sb.toString();
    }
}
