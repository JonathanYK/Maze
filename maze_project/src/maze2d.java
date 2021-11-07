import java.util.*;

public class maze2d {

    int mazeSize;
    boolean[][] mazeStructure;
    mazePoint entrance;
    mazePoint exit;

    mazePoint[][] mazePreviewPoints;

    ArrayList<?> solutionBFS;
    ArrayList<?> solutionAstar;


    public maze2d(int mazeSize) {

        // initiating maze structure (default params are false):
        this.mazeStructure = new boolean[mazeSize][mazeSize];

        this.mazeSize = mazeSize;

        // default entrance point is (0,0):
        this.entrance = new mazePoint(0, 0);

        // default exit is the bottom right corner:
        this.exit = new mazePoint(mazeSize - 1, mazeSize - 1);
    }

    public mazePoint getEntrance() { return entrance; }

    public mazePoint getExit() {
        return exit;
    }

    public boolean[][] getMazeStructure() {
        return mazeStructure;
    }

    public int getMazeSize() {
        return mazeSize;
    }

    public boolean getCoorVal(int x, int y) { return this.mazeStructure[x][y]; }

    public void setPointCoorToMazeVal(mazePoint p) {
        this.mazeStructure[p.getX()][p.getY()] = true;
    }

    public void setEntrance(mazePoint entrance) {
        this.entrance =  entrance;
    }

    public void setExit(mazePoint exit) {
        this.exit = exit;
    }

    public void setCurrMazePoint(int x, int y, boolean val) {
        this.mazeStructure[x][y] = val;
    }

    public void setMazeSize(int mazeSize) {
        this.mazeSize = mazeSize;
    }


    public void setCustomMaze (boolean[][] customMaze) {
        for (int i = 0; i<= this.mazeSize-1; i++) {
            for (int j = 0; j<= this.mazeSize-1; j++)
                this.mazeStructure[i][j] = customMaze[i][j];
        }
    }


    // used to get only the closed moves (if it's not a wall):
    public ArrayList<mazePoint> getAvailableMoves (mazePoint currPoint) {
        return getAvailableNeighborsMain(currPoint, this.mazePreviewPoints, false, true);
    }

    // get the available unvisited neighbors:
    public ArrayList<mazePoint> getAvailableNeighbors (mazePoint currPoint, mazePoint[][] mazePoints) {
        return getAvailableNeighborsMain(currPoint, mazePoints, false, false);
    }

    // get neighbors in order to build the maze:
    public ArrayList<mazePoint> getAvailableNeighbors (mazePoint currPoint, mazePoint[][] mazePoints, boolean mazeBuilding) {
        return getAvailableNeighborsMain(currPoint, mazePoints, mazeBuilding, false);
    }


    // get neighbors with casting to mazeStarPoint:
    public ArrayList<mazeStarPoint> getAvailableNeighbors (mazeStarPoint currStarPoint, mazeStarPoint[][] mazeStarPoints) {

        ArrayList<mazePoint> mazePointsRet = getAvailableNeighborsMain(currStarPoint, mazeStarPoints, false, true);

        ArrayList<mazeStarPoint> availableNeighbors = new ArrayList<>();
        for (mazePoint mp : mazePointsRet)
            availableNeighbors.add((mazeStarPoint)mp);

        return availableNeighbors;
    }


    public ArrayList<mazePoint> getAvailableNeighborsMain(mazePoint currPoint, mazePoint[][] mazePoints,
                                                          boolean mazeBuilding, boolean getNeigborsOnly) {

        ArrayList<mazePoint> availableNeighbors = new ArrayList<>();

        // North move:
        if(currPoint.getX() != 0) {

            mazePoint northPoint = mazePoints[currPoint.getX() - 1][currPoint.getY()];

            if(!northPoint.isVisited()) {
                if (mazeBuilding)
                    availableNeighbors.add(northPoint);
                else {
                    if (this.getCoorVal(northPoint.getX(), northPoint.getY()) && !northPoint.isVisited())
                        availableNeighbors.add(northPoint);
                }
            }
            else if (getNeigborsOnly)
                availableNeighbors.add(northPoint);
        }

        // South move:
        if(currPoint.getX() < this.mazeSize-1) {

            mazePoint southPoint = mazePoints[currPoint.getX() + 1][currPoint.getY()];

            if (!southPoint.isVisited()) {
                if (mazeBuilding)
                    availableNeighbors.add(southPoint);
                else {
                    if (this.getCoorVal(southPoint.getX(), southPoint.getY()) && !southPoint.isVisited())
                        availableNeighbors.add(southPoint);
                }
            }
            else if (getNeigborsOnly)
                availableNeighbors.add(southPoint);
        }

        // West move:
        if(currPoint.getY() != 0) {

            mazePoint westPoint = mazePoints[currPoint.getX()][currPoint.getY() - 1];

            if (!westPoint.isVisited()) {
                if (mazeBuilding)
                    availableNeighbors.add(westPoint);
                else {
                    if (this.getCoorVal(westPoint.getX(), westPoint.getY()) && !westPoint.isVisited())
                        availableNeighbors.add(westPoint);
                }
            }
            else if (getNeigborsOnly)
                availableNeighbors.add(westPoint);
        }

        // East move:
        if(currPoint.getY() < this.mazeSize-1) {

            mazePoint eastPoint = mazePoints[currPoint.getX()][currPoint.getY() + 1];

            if (!eastPoint.isVisited()) {
                if (mazeBuilding)
                    availableNeighbors.add(eastPoint);
                else {
                    if (this.getCoorVal(eastPoint.getX(), eastPoint.getY()) && !eastPoint.isVisited())
                        availableNeighbors.add(eastPoint);
                }
            }
            else if (getNeigborsOnly)
                availableNeighbors.add(eastPoint);
        }
        return availableNeighbors;
    }


    // This method used for getting dual neighbors (first nearest and second nearest from North/South/East/West):
    public void getDualAvailNeighbors( mazePoint[][] pointsPath, mazePoint[] currDualNeighbor, ArrayList<mazePoint[]> mazeStructure) {

        // only the SECOND nearest is relevant:
        int secX = currDualNeighbor[1].getX();
        int secY = currDualNeighbor[1].getY();

        // North move:
        if(secX>=2 && !pointsPath[secX-2][secY].isVisited()) {
            mazeStructure.add(new mazePoint[] {pointsPath[secX-1][secY], pointsPath[secX-2][secY]});
        }

        // South move:
        if(secX<this.mazeSize-2 && !pointsPath[secX+2][secY].isVisited()) {
            mazeStructure.add(new mazePoint[] {pointsPath[secX+1][secY], pointsPath[secX+2][secY]});
        }

        // West move:
        if(secY>=2 && !pointsPath[secX][secY-2].isVisited()) {
            mazeStructure.add(new mazePoint[] {pointsPath[secX][secY-1], pointsPath[secX][secY-2]});
        }

        // East move:
        if(secY<this.mazeSize-2 && !pointsPath[secX][secY+2].isVisited()) {
            mazeStructure.add(new mazePoint[] {pointsPath[secX][secY+1], pointsPath[secX][secY+2]});
        }
    }


    public String solutionRoutePrinter(ArrayList<?> solution) {
        final StringBuilder sb = new StringBuilder();

                Collections.reverse(solution);
                while (!solution.isEmpty()) {
                    mazePoint currPnt = (mazePoint) solution.remove(0);
                    sb.append("(" + currPnt.getX() + ", " + currPnt.getY() + ")");

                    if (!solution.isEmpty())
                        sb.append(" -> ");
                }
        return sb.toString();
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




