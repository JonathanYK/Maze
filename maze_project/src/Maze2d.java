
public class Maze2d {

    Point entrance;
    Point exit;
    boolean[][] currMaze;
    int mazeSize;


    public Maze2d(int mazeSize) {

        this.currMaze = new boolean[mazeSize][mazeSize];
        java.util.Arrays.fill(this.currMaze[0], false);
        java.util.Arrays.fill(this.currMaze[1], false);

        this.mazeSize = mazeSize;
        this.entrance = new Point(0, 0);
        this.exit = new Point(mazeSize - 1, mazeSize - 1);
    }


    public Point getEntrance() {
        return entrance;
    }

    public Point getExit() {
        return exit;
    }

    public boolean[][] getCurrMaze() {
        return currMaze;
    }

    public int getMazeSize() {
        return mazeSize;
    }

    public void setEntrance(Point entrance) {
        this.entrance = entrance;
    }

    public void setExit(Point exit) {
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
        String retStr = "";

        for (int i = 0; i < this.mazeSize + 2; i++) {
            retStr = retStr.concat("#");
        }
        retStr = retStr.concat("\n");


        for (int i = 0; i < this.mazeSize; i++) {
            retStr = retStr.concat("#");
            for (int j = 0; j < this.mazeSize; j++) {

                // Set entrance and exit point as "X":
                if ( i == 0 && j == 0 || i == this.mazeSize-1 && j == this.mazeSize-1) {
                    retStr = retStr.concat("X");
                    continue;
                }

                retStr = retStr.concat(String.valueOf(this.currMaze[i][j] ? '#' : '.'));
            }
            retStr = retStr.concat("#").concat("\n");
        }

        for (int i = 0; i < this.mazeSize + 2; i++) {
            retStr = retStr.concat("#");
        }


        // Printing zeroes and ones separated by ','

//        for (int i = 0; i< this.mazeSize; i++) {
//            for (int j = 0; j < this.mazeSize-1; j++) {
//                retStr = retStr.concat(String.valueOf(Boolean.compare(this.currMaze[i][j], false))).concat(", ");
//            }
//            retStr = retStr.concat(String.valueOf(Boolean.compare(this.currMaze[i][mazeSize-1], false)));
//            retStr = retStr.concat("\n");
//        }


        return retStr;
    }
}



