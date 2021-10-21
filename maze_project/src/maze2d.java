
public class maze2d {

    mazePoint entrance;
    mazePoint exit;
    boolean[][] currMaze;
    int mazeSize;


    public maze2d(int mazeSize) {

        this.currMaze = new boolean[mazeSize][mazeSize];
        java.util.Arrays.fill(this.currMaze[0], false);
        java.util.Arrays.fill(this.currMaze[1], false);

        this.mazeSize = mazeSize;
        this.entrance = new mazePoint(0, 0);
        this.exit = new mazePoint(mazeSize - 1, mazeSize - 1);
    }


    public mazePoint getEntrance() {
        return entrance;
    }

    public mazePoint getExit() {
        return exit;
    }

    public boolean[][] getCurrMaze() {
        return currMaze;
    }

    public int getMazeSize() {
        return mazeSize;
    }


    public void setPointCoorToMazeVal(mazePoint p) {
        this.currMaze[p.getX()][p.getY()] = true;
    }

    public void setEntrance(mazePoint entrance) {
        this.entrance = entrance;
    }

    public void setExit(mazePoint exit) {
        this.exit = exit;
    }

    public void setCurrMazePoint(int x, int y, boolean val) {
        this.currMaze[x][y] = val;
    }

    public void setMazeSize(int mazeSize) {
        this.mazeSize = mazeSize;
    }


    @Override
    public String toString() {
        final char PATH_CHAR = ' ';
        final char WALL_CHAR = '▓';

        final StringBuilder sb = new StringBuilder();

        // add upper maze wall:
        sb.append(String.valueOf(WALL_CHAR).repeat(this.mazeSize + 2));
        sb.append('\n');

        for (int i = 0; i < this.mazeSize; i++) {
            sb.append(WALL_CHAR);

            for (int j = 0; j < this.mazeSize; j++)
                sb.append(this.currMaze[i][j] ? WALL_CHAR : PATH_CHAR);
            sb.append(WALL_CHAR);
            sb.append('\n');
        }

        // add bottom maze wall:
        sb.append(String.valueOf(WALL_CHAR).repeat(this.mazeSize + 2));
        sb.append('\n');

        return sb.toString();
    }
}




