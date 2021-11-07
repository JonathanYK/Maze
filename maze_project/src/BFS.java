import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class BFS extends Solution {


    public ArrayList<?> bfs(maze2d maze) {

        // shortest (solution) path:
        ArrayList<?> solution = new ArrayList<>();

        // queue that will store all the paths:
        Queue<mazePoint> mazePointsQueue = new LinkedList<>();


        // creating the actual preview of maze using mazePoints:
        maze.mazePreviewPoints = new mazePoint[maze.mazeSize][maze.mazeSize];
        for (int i = 0; i < maze.mazeSize; i++)
            for (int j = 0; j < maze.mazeSize; j++)
                maze.mazePreviewPoints[i][j] = new mazePoint(i, j);

        // adding the start point to the queue:
        mazePointsQueue.add(maze.getEntrance().getPointOnMazePoints(maze.mazePreviewPoints));


        while (!mazePointsQueue.isEmpty()) {

            // pulling the current point and setting it as visited:
            mazePoint currPoint = mazePointsQueue.remove();
            // mazePoints[currPoint.getX()][currPoint.getY()].setVisited(true);
            currPoint.setVisited(true);

            // breaking rule, shortest route to exit point found:
            if (currPoint.equals(maze.getExit())) {
                getFullRoute(solution, currPoint);
                return solution;
            }

            // getting all available neigbors, then assigning to all of them their parent (currPoint):
            ArrayList<mazePoint> avaiNeigh = maze.getAvailableNeighbors(currPoint, maze.mazePreviewPoints);
            for (mazePoint mp : avaiNeigh)
                mp.setParent(currPoint);

            // adding all available neighbors with actual parent of currPoint to the main queue:
            mazePointsQueue.addAll(avaiNeigh);
        }
        return solution;
    }

}
