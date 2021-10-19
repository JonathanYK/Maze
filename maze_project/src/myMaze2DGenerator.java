import java.util.*;

public class myMaze2DGenerator extends abstractMaze2DGenerator {

    maze2d myMaze;

    @Override
    public maze2d generate(int mazeSize) {
        this.myMaze = new maze2d(mazeSize);

        // creating whole maze using random Prim's algorithm:
        createRandMazePrim();

        return this.myMaze;
    }


    private void createRandMazePrim() {

        Random rand = new Random();
        point[][] pointsPath = new point[this.myMaze.mazeSize][this.myMaze.mazeSize];
        LinkedList<point[]> mazeStructure = new LinkedList<>();

        for (int i = 0; i < this.myMaze.mazeSize; i++)
            for (int j = 0; j < this.myMaze.mazeSize; j++)
                pointsPath[i][j] = new point(i, j);

        // generating random coordinate for start point:
        int randPointCoord = rand.nextInt(this.myMaze.mazeSize);

        // starting point can be forced to be (0,0):
        // point zeroZeroStartingPoint = this.pointsPath[0][0];

        // inserting random start point to the maze structure:
        point randStartingPoint = pointsPath[randPointCoord][randPointCoord];
        mazeStructure.add(new point[] {randStartingPoint, randStartingPoint});


        // while there are unvisited available dual neighbors:
        while (!mazeStructure.isEmpty()) {

            // get random dual neighbor:
            point[] currDualNeighbor = mazeStructure.remove(rand.nextInt(mazeStructure.size()));

            // in case the SECOND nearest isn't visited (no path between them):
            if(!pointsPath[currDualNeighbor[1].getX()][currDualNeighbor[1].getY()].isVisited()) {

                // set both first nearest and second nearest as visited (create path):
                pointsPath[currDualNeighbor[0].getX()][currDualNeighbor[0].getY()].setVisited();
                pointsPath[currDualNeighbor[1].getX()][currDualNeighbor[1].getY()].setVisited();

                // set visited both neighbors on myMaze:
                myMaze.setPointCoorToMazeVal(currDualNeighbor[0]);
                myMaze.setPointCoorToMazeVal(currDualNeighbor[1]);

                // get all dual available neighbors:
                getDualAvailNeighbors(pointsPath, currDualNeighbor, mazeStructure);
            }
        }
    }


    // This method used for getting dual neighbors (first nearest and second nearest from North/South/East/West):
    private void getDualAvailNeighbors(point[][] pointsPath, point[] currDualNeighbor, LinkedList<point[]> mazeStructure) {

        // only the SECOND nearest is relevant:
        int secX = currDualNeighbor[1].getX();
        int secY = currDualNeighbor[1].getY();

        // North move:
        if(secX>=2 && !pointsPath[secX-2][secY].isVisited()) {
                mazeStructure.add(new point[] {pointsPath[secX-1][secY], pointsPath[secX-2][secY]});
        }

        // South move:
        if(secX<this.myMaze.mazeSize-2 && !pointsPath[secX+2][secY].isVisited()) {
            mazeStructure.add(new point[] {pointsPath[secX+1][secY], pointsPath[secX+2][secY]});
        }

        // West move:
        if(secY>=2 && !pointsPath[secX][secY-2].isVisited()) {
            mazeStructure.add(new point[] {pointsPath[secX][secY-1], pointsPath[secX][secY-2]});
        }

        // East move:
        if(secY<this.myMaze.mazeSize-2 && !pointsPath[secX][secY+2].isVisited()) {
            mazeStructure.add(new point[] {pointsPath[secX][secY+1], pointsPath[secX][secY+2]});
        }
    }

}
