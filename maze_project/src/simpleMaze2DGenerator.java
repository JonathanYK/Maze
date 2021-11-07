import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class simpleMaze2DGenerator extends abstractMaze2DGenerator {

    @Override
    public maze2d generate(int mazeSize) {
        maze2d maze = new maze2d(mazeSize);

        // set Random walls on generated maze:
        setRandomWalls(maze);

        // create path from entrance to exit points recursive way (not in use):
        //createPathRecursively(maze);

        Stack<mazePoint> currDFSPath = createPathIteratively(maze);

        // clear the maze according to the path:
        clearMazeWithRoute(maze, currDFSPath);

        return maze;
    }

    private void setRandomWalls(maze2d maze) {

        Random rand = new Random();
        for (int i = 0; i< maze.getMazeSize(); i++) {
            for (int j = 0; j < maze.getMazeSize(); j++) {
                maze.setCurrMazePoint(i, j, rand.nextBoolean());
            }
        }
    }

    // not in use due to recursion limitations:
    private void createPathRecursively(maze2d maze) {

        Stack <mazePoint> currDFSPath = new Stack<>();
        mazePoint[][] mazePoints = new mazePoint[maze.getMazeSize()][maze.getMazeSize()];
        for (int i = 0; i < maze.getMazeSize(); i++)
            for (int j = 0; j < maze.getMazeSize(); j++)
                mazePoints[i][j] = new mazePoint(i, j);

        pathBuilderDFS(maze, maze.getEntrance(), mazePoints, currDFSPath);
    }

    private boolean pathBuilderDFS(maze2d maze, mazePoint currPoint, mazePoint[][] mazePoints, Stack<mazePoint> currDFSPath) {

            // if goal is reached (current point is the exit point):
            if (currPoint.equals(maze.getExit())) {
                currDFSPath.push(currPoint);
                return true;
            }

            // Setting current point as visited
            mazePoints[currPoint.getX()][currPoint.getY()].setVisited(true);

            // Getting all available neighbors:
            ArrayList<mazePoint> currMazePointNeighbors = maze.getAvailableNeighbors(currPoint, mazePoints);

            // If there are unvisited neighbors:
            if (currMazePointNeighbors.size() > 0) {
                if (currMazePointNeighbors.size() > 1)
                    // Choose next neighbor randomly in order to create different paths on each iteration:
                    Collections.shuffle(currMazePointNeighbors);

                // Adding the current point to the route and moving to next point recursively:
                currDFSPath.push(currPoint);
                pathBuilderDFS(maze, currMazePointNeighbors.get(0), mazePoints, currDFSPath);
            }
            else {
                // If there aren't unvisited neighbors (traceback):
                pathBuilderDFS(maze, currDFSPath.pop(), mazePoints, currDFSPath);
            }
        return false;
    }


    private Stack<mazePoint> createPathIteratively(maze2d maze) {
        Stack<mazePoint> routesTrace = new Stack<>();
        mazePoint[][] mazePoints = new mazePoint[maze.getMazeSize()][maze.getMazeSize()];

        for (int i = 0; i < maze.getMazeSize(); i++)
            for (int j = 0; j < maze.getMazeSize(); j++)
                mazePoints[i][j] = new mazePoint(i, j);

        routesTrace.push(maze.getEntrance());

        while (!routesTrace.empty()) {

            mazePoint currMazePoint = routesTrace.pop();

            if (currMazePoint.equals(maze.getExit())) {
                routesTrace.push(currMazePoint);
                return getSolutionPath(maze, routesTrace.pop());
            }

            // Getting all available neighbors:
            ArrayList<mazePoint> currMazePointNeighbors = maze.getAvailableNeighbors(currMazePoint, mazePoints, true);

            // Choose next neighbor randomly in order to create different paths on each iteration:
            Collections.shuffle(currMazePointNeighbors);

            for (mazePoint neighbor: currMazePointNeighbors) {
                neighbor.setVisited(true);
                neighbor.setParent(currMazePoint);
                routesTrace.push(neighbor);
            }
        }
        return routesTrace;
    }

    private Stack<mazePoint> getSolutionPath(maze2d maze, mazePoint revSolutionMazePoint) {
        Stack <mazePoint> currDFSPath = new Stack<>();

        // Adding the start point:
        currDFSPath.push(new mazePoint(maze.getEntrance().getX(), maze.getEntrance().getY()));

        while (revSolutionMazePoint.getParent() != null) {
            currDFSPath.push(new mazePoint(revSolutionMazePoint.getX(), revSolutionMazePoint.getY()));
            revSolutionMazePoint = revSolutionMazePoint.getParent();
        }
        return currDFSPath;
    }

    private void clearMazeWithRoute(maze2d maze, Stack<mazePoint> currDFSPath) {

        while (!currDFSPath.empty()) {
            mazePoint currRouteMazePoint = currDFSPath.pop();
            maze.setCurrMazePoint(currRouteMazePoint.getX(), currRouteMazePoint.getY(), true);
        }
    }
}
