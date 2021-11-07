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
        mazePoint defStartingMazePoint = pointsPath[maze.getEntrance().getX()][maze.getEntrance().getY()];
        mazeStructure.add(new mazePoint[] {defStartingMazePoint, defStartingMazePoint});


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
                maze.setPointCoorToMazeVal(currDualNeighbor[0]);
                maze.setPointCoorToMazeVal(currDualNeighbor[1]);

                // get all dual available neighbors:
                maze.getDualAvailNeighbors(pointsPath, currDualNeighbor, mazeStructure);
            }
        }
    }
}
