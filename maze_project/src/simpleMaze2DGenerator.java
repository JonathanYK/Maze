import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class simpleMaze2DGenerator extends abstractMaze2DGenerator {

    maze2d simpleMaze;
    Stack<point> solutionRoute;

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
        point[][] points = new point[this.simpleMaze.mazeSize][this.simpleMaze.mazeSize];
        for (int i = 0; i < this.simpleMaze.mazeSize; i++)
            for (int j = 0; j < this.simpleMaze.mazeSize; j++)
                points[i][j] = new point(i, j);

        pathBuilderDFS(simpleMaze.getEntrance(), points);
    }


    private boolean pathBuilderDFS(point entrance, point[][] points) {

            // if goal is reached (current point is the exit point):
            if (entrance.equals(this.simpleMaze.getExit())) {
                solutionRoute.push(entrance);
                return true;
            }

            // Setting current point as visited
            points[entrance.getX()][entrance.getY()].setVisited();

            // Getting all available neighbors:
            ArrayList<point> currPointNeighbors = entrance.getAvailableNeighbors(this.simpleMaze.mazeSize, points);

            // If there are unvisited neighbors:
            if (currPointNeighbors.size() > 0) {
                if (currPointNeighbors.size() > 1)
                    // Choose next neighbor randomly in order to create different paths on each iteration:
                    Collections.shuffle(currPointNeighbors);

                // Adding the current point to the route and moving to next point recursively:
                solutionRoute.push(entrance);
                pathBuilderDFS(currPointNeighbors.get(0), points);
            }
            else {
                // If there aren't unvisited neighbors (traceback):
                pathBuilderDFS(solutionRoute.pop(), points);
            }
        return false;
    }


    private void createPathIteratively() {

        Stack<point> routesTrace = new Stack<>();
        point[][] points = new point[this.simpleMaze.mazeSize][this.simpleMaze.mazeSize];

        for (int i = 0; i < this.simpleMaze.mazeSize; i++)
            for (int j = 0; j < this.simpleMaze.mazeSize; j++)
                points[i][j] = new point(i, j);

        routesTrace.push(this.simpleMaze.entrance);

        while (!routesTrace.empty()) {

            point currPoint = routesTrace.pop();
            if (currPoint.equals(this.simpleMaze.getExit())) {
                routesTrace.push(currPoint);
                getSolutionPath(routesTrace);
            }

            // Getting all available neighbors:
            ArrayList<point> currPointNeighbors = currPoint.getAvailableNeighbors(this.simpleMaze.mazeSize, points);

            // Choose next neighbor randomly in order to create different paths on each iteration:
            Collections.shuffle(currPointNeighbors);

            for (point neighbor: currPointNeighbors) {
                neighbor.setVisited();
                neighbor.setParent(currPoint);
                routesTrace.push(neighbor);
            }
        }
    }


    private void getSolutionPath(Stack<point> routesTrace) {
        this.solutionRoute = new Stack<>();
        point revSolutionPoint = routesTrace.pop();

        while (revSolutionPoint.getParent() != null) {
            this.solutionRoute.push(revSolutionPoint);
            revSolutionPoint = revSolutionPoint.getParent();
        }

        // Adding the start point:
        this.solutionRoute.push(revSolutionPoint);
    }


    private void clearMazeWithRoute() {

        while (!this.solutionRoute.empty()) {
            point currRoutePoint = this.solutionRoute.pop();
            this.simpleMaze.setCurrMazePoint(currRoutePoint.getX(), currRoutePoint.getY(), false);
        }
    }
}
