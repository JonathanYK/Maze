import java.util.*;

public class myMaze2DGenerator extends abstractMaze2DGenerator {

    @Override
    public maze2d generate(int mazeSize) {
        maze2d maze = new maze2d(mazeSize);

        // creating whole maze using random Prim's algorithm:
        createRandMazePrim(maze);

        return maze;
    }

    private void createRandMazePrim(maze2d maze) {

        Random rand = new Random();
        mazePoint[][] pointsPath = new mazePoint[maze.mazeSize][maze.mazeSize];
        ArrayList<mazePoint[]> mazeStructure = new ArrayList<>();

        for (int i = 0; i < maze.mazeSize; i++)
            for (int j = 0; j < maze.mazeSize; j++)
                pointsPath[i][j] = new mazePoint(i, j);

        // inserting start point to the maze structure:
        mazePoint entranceMazePoint = pointsPath[maze.getEntrance().x][maze.getEntrance().y];
        mazeStructure.add(new mazePoint[] {entranceMazePoint, entranceMazePoint});


        // while there are unvisited available dual neighbors:
        while (!mazeStructure.isEmpty()) {

            // get random dual neighbor:
            mazePoint[] currDualNeighbor = mazeStructure.remove(rand.nextInt(mazeStructure.size()));

            // in case the SECOND nearest isn't visited (no path between them):
            if(!pointsPath[currDualNeighbor[1].getX()][currDualNeighbor[1].getY()].isVisited()) {

                // set both first nearest and second nearest as visited (create path):
                pointsPath[currDualNeighbor[0].getX()][currDualNeighbor[0].getY()].setVisited(true);
                pointsPath[currDualNeighbor[1].getX()][currDualNeighbor[1].getY()].setVisited(true);

                // set visited both neighbors on myMaze:
                maze.setPointVal(currDualNeighbor[0].getX(), currDualNeighbor[0].getY(), true);
                maze.setPointVal(currDualNeighbor[1].getX(), currDualNeighbor[1].getY(), true);

                // get all dual available neighbors:
                getDualAvailNeighbors(maze, pointsPath, currDualNeighbor, mazeStructure);
            }
        }
    }

    // This method used for getting dual neighbors (first nearest and second nearest from North/South/East/West):
    public void getDualAvailNeighbors(maze2d maze, mazePoint[][] pointsPath, mazePoint[] currDualNeighbor,
                                      ArrayList<mazePoint[]> mazeStructure) {

        // only the SECOND nearest is relevant:
        int secX = currDualNeighbor[1].getX();
        int secY = currDualNeighbor[1].getY();

        // North move:
        if(secX>=2 && !pointsPath[secX-2][secY].isVisited()) {
            mazeStructure.add(new mazePoint[] {pointsPath[secX-1][secY], pointsPath[secX-2][secY]});
        }

        // South move:
        if(secX<maze.mazeSize-2 && !pointsPath[secX+2][secY].isVisited()) {
            mazeStructure.add(new mazePoint[] {pointsPath[secX+1][secY], pointsPath[secX+2][secY]});
        }

        // West move:
        if(secY>=2 && !pointsPath[secX][secY-2].isVisited()) {
            mazeStructure.add(new mazePoint[] {pointsPath[secX][secY-1], pointsPath[secX][secY-2]});
        }

        // East move:
        if(secY<maze.mazeSize-2 && !pointsPath[secX][secY+2].isVisited()) {
            mazeStructure.add(new mazePoint[] {pointsPath[secX][secY+1], pointsPath[secX][secY+2]});
        }
    }
}
