package controller;

public class mazePoint {
    private int x;
    private int y;
    private mazePoint parent;
    private boolean visited = false;

    public mazePoint(int x, int y) {
        this.x = x;
        this.y = y;
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

    public void setVisited(boolean val) {
        this.visited = val;
    }

    public void setParent(mazePoint p) {
        this.parent = p;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\ncontroller.mazePoint: [");
        sb.append(this.getX());
        sb.append("][");
        sb.append(this.getY());
        sb.append("]");

        return sb.toString();
    }

}
