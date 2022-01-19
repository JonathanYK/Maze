package model;

import java.util.*;

public class MyIIMaze2DGenerator extends AbstractIMaze2DGenerator {

    @Override
    public Maze2d generate(int mazeSize) {
        Maze2d maze = new Maze2d(mazeSize);

        // creating whole maze using random Prim's algorithm:
        createRandMazePrim(maze);

        return maze;
    }

    private void createRandMazePrim(Maze2d maze) {

        Random rand = new Random();
        MazePoint[][] pointsPath = new MazePoint[maze.mazeSize][maze.mazeSize];
        ArrayList<MazePoint[]> mazeStructure = new ArrayList<>();

        for (int i = 0; i < maze.mazeSize; i++)
            for (int j = 0; j < maze.mazeSize; j++)
                pointsPath[i][j] = new MazePoint(i, j);

        // inserting start point to the maze structure:
        MazePoint entranceMazePoint = pointsPath[maze.getEntrance().x][maze.getEntrance().y];
        mazeStructure.add(new MazePoint[] {entranceMazePoint, entranceMazePoint});


        // while there are unvisited available dual neighbors:
        while (!mazeStructure.isEmpty()) {

            // get random dual neighbor:
            MazePoint[] currDualNeighbor = mazeStructure.remove(rand.nextInt(mazeStructure.size()));

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
        // set the exit point as a valid path:
        maze.setPointVal((int) maze.exit.getX(), (int) maze.exit.getX(), true);

        // While using even maze, we should clear manually the cell near the exit:
        if (maze.mazeSize%2 == 0)
            maze.setPointVal(maze.mazeSize-2, maze.mazeSize-1, true);

    }

    // This method used for getting dual neighbors (first nearest and second nearest from North/South/East/West):
    public void getDualAvailNeighbors(Maze2d maze, MazePoint[][] pointsPath, MazePoint[] currDualNeighbor,
                                      ArrayList<MazePoint[]> mazeStructure) {

        // We'll search for two neighbors where between them a wall - then we'll destroy this wall.


        // only the SECOND nearest is relevant:
        int secX = currDualNeighbor[1].getX();
        int secY = currDualNeighbor[1].getY();

        // North move:
        if(secX>=2 && !pointsPath[secX-2][secY].isVisited()) {
            mazeStructure.add(new MazePoint[] {pointsPath[secX-1][secY], pointsPath[secX-2][secY]});
        }

        // South move:
        if(secX<maze.mazeSize-2 && !pointsPath[secX+2][secY].isVisited()) {
            mazeStructure.add(new MazePoint[] {pointsPath[secX+1][secY], pointsPath[secX+2][secY]});
        }

        // West move:
        if(secY>=2 && !pointsPath[secX][secY-2].isVisited()) {
            mazeStructure.add(new MazePoint[] {pointsPath[secX][secY-1], pointsPath[secX][secY-2]});
        }

        // East move:
        if(secY<maze.mazeSize-2 && !pointsPath[secX][secY+2].isVisited()) {
            mazeStructure.add(new MazePoint[] {pointsPath[secX][secY+1], pointsPath[secX][secY+2]});
        }
    }
}
