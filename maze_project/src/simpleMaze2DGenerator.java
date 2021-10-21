import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class simpleMaze2DGenerator extends abstractMaze2DGenerator {

    maze2d simpleMaze;
    Stack<mazePoint> solutionRoute;

    @Override
    public maze2d generate(int mazeSize) {
        this.simpleMaze = new maze2d(mazeSize);

        // set Random walls on generated maze:
        setRandomWalls();

        // create path from entrance to exit points recursive way (not in use):
        //createPathRecursively();

        createPathIteratively();

        // clear the maze according to the path:
        clearMazeWithRoute();

        return this.simpleMaze;
    }


    private void setRandomWalls() {

        Random rand = new Random();
        for (int i = 0; i< this.simpleMaze.mazeSize; i++) {
            for (int j = 0; j < this.simpleMaze.mazeSize; j++) {
                this.simpleMaze.setCurrMazePoint(i, j, rand.nextBoolean());
            }
        }
    }


    private void createPathRecursively() {

        this.solutionRoute = new Stack<>();
        mazePoint[][] mazePoints = new mazePoint[this.simpleMaze.mazeSize][this.simpleMaze.mazeSize];
        for (int i = 0; i < this.simpleMaze.mazeSize; i++)
            for (int j = 0; j < this.simpleMaze.mazeSize; j++)
                mazePoints[i][j] = new mazePoint(i, j);

        pathBuilderDFS(simpleMaze.getEntrance(), mazePoints);
    }


    private boolean pathBuilderDFS(mazePoint entrance, mazePoint[][] mazePoints) {

            // if goal is reached (current point is the exit point):
            if (entrance.equals(this.simpleMaze.getExit())) {
                solutionRoute.push(entrance);
                return true;
            }

            // Setting current point as visited
            mazePoints[entrance.getX()][entrance.getY()].setVisited(true);

            // Getting all available neighbors:
            ArrayList<mazePoint> currMazePointNeighbors = entrance.getAvailableNeighbors(this.simpleMaze.mazeSize, mazePoints);

            // If there are unvisited neighbors:
            if (currMazePointNeighbors.size() > 0) {
                if (currMazePointNeighbors.size() > 1)
                    // Choose next neighbor randomly in order to create different paths on each iteration:
                    Collections.shuffle(currMazePointNeighbors);

                // Adding the current point to the route and moving to next point recursively:
                solutionRoute.push(entrance);
                pathBuilderDFS(currMazePointNeighbors.get(0), mazePoints);
            }
            else {
                // If there aren't unvisited neighbors (traceback):
                pathBuilderDFS(solutionRoute.pop(), mazePoints);
            }
        return false;
    }


    private void createPathIteratively() {

        Stack<mazePoint> routesTrace = new Stack<>();
        mazePoint[][] mazePoints = new mazePoint[this.simpleMaze.mazeSize][this.simpleMaze.mazeSize];

        for (int i = 0; i < this.simpleMaze.mazeSize; i++)
            for (int j = 0; j < this.simpleMaze.mazeSize; j++)
                mazePoints[i][j] = new mazePoint(i, j);

        routesTrace.push(this.simpleMaze.entrance);

        while (!routesTrace.empty()) {

            mazePoint currMazePoint = routesTrace.pop();
            if (currMazePoint.equals(this.simpleMaze.getExit())) {
                routesTrace.push(currMazePoint);
                getSolutionPath(routesTrace);
            }

            // Getting all available neighbors:
            ArrayList<mazePoint> currMazePointNeighbors = currMazePoint.getAvailableNeighbors(this.simpleMaze.mazeSize, mazePoints);

            // Choose next neighbor randomly in order to create different paths on each iteration:
            Collections.shuffle(currMazePointNeighbors);

            for (mazePoint neighbor: currMazePointNeighbors) {
                neighbor.setVisited(true);
                neighbor.setParent(currMazePoint);
                routesTrace.push(neighbor);
            }
        }
    }


    private void getSolutionPath(Stack<mazePoint> routesTrace) {
        this.solutionRoute = new Stack<>();
        mazePoint revSolutionMazePoint = routesTrace.pop();

        while (revSolutionMazePoint.getParent() != null) {
            this.solutionRoute.push(revSolutionMazePoint);
            revSolutionMazePoint = revSolutionMazePoint.getParent();
        }

        // Adding the start point:
        this.solutionRoute.push(revSolutionMazePoint);
    }


    private void clearMazeWithRoute() {

        while (!this.solutionRoute.empty()) {
            mazePoint currRouteMazePoint = this.solutionRoute.pop();
            this.simpleMaze.setCurrMazePoint(currRouteMazePoint.getX(), currRouteMazePoint.getY(), false);
        }
    }
}
