package model;

public class MazePoint {

    private int x;
    private int y;
    private MazePoint parent;
    private boolean visited = false;

    public MazePoint(int x, int y) {
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

    public MazePoint getParent() {
        return this.parent;
    }

    public void setVisited(boolean val) {
        this.visited = val;
    }

    public void setParent(MazePoint p) {
        this.parent = p;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\nMazePoint: [");
        sb.append(this.getX());
        sb.append("][");
        sb.append(this.getY());
        sb.append("]");
        return sb.toString();
    }
}
