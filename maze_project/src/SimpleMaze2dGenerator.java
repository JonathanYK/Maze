import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class SimpleMaze2dGenerator extends AbstractMaze2dGenerator {

    Maze2d simpleMaze;
    Stack<Point> solutionRoute;

    @Override
    public Maze2d generate(int mazeSize) {
        this.simpleMaze = new Maze2d(mazeSize);

        // set Random walls on generated maze:
        setRandomWalls();

        // create path from entrance to exit points recursive way:
        createPathRecursively();

        // clear the maze according to the path:
        clearMazeWithRoute();

        return this.simpleMaze;
    }


    private Maze2d setRandomWalls() {

        Random rand = new Random();
        for (int i = 0; i< this.simpleMaze.mazeSize; i++) {
            for (int j = 0; j < this.simpleMaze.mazeSize; j++) {
                this.simpleMaze.setCurrMazePoint(i, j, rand.nextBoolean());
            }
        }
        return this.simpleMaze;
    }

    private void createPathRecursively() {

        this.solutionRoute = new Stack<>();
        Point[][] points = new Point[this.simpleMaze.mazeSize][this.simpleMaze.mazeSize];
        for (int i = 0; i < this.simpleMaze.mazeSize; i++)
            for (int j = 0; j < this.simpleMaze.mazeSize; j++)
                points[i][j] = new Point(i, j);

        pathBuilderDFS(simpleMaze.getEntrance(), points);
    }



    private boolean pathBuilderDFS(Point entrance, Point[][] points) {

            // if goal is reached (current point is the exit point):
            if (entrance.equals(this.simpleMaze.getExit())) {
                solutionRoute.push(entrance);
                return true;
            }

            // Setting current point as visited
            points[entrance.getX()][entrance.getY()].setVisited();

            // Getting all available neighbors:
            ArrayList<Point> currPointNeighbors = entrance.getAvailableNeighbors(this.simpleMaze.mazeSize, points);

            // If there are unvisited neighbors:
            if (currPointNeighbors.size() > 0) {
                if (currPointNeighbors.size() > 1)
                    // Choose next neighbor randomly in order to create different paths on each iteration:
                    Collections.shuffle(currPointNeighbors);

                // Adding the corrent point to the route and moving to next point recursively:
                solutionRoute.push(entrance);
                pathBuilderDFS(currPointNeighbors.get(0), points);
            }
            else {
                // If there aren't unvisited neighbors (traceback):
                pathBuilderDFS(solutionRoute.pop(), points);
            }
        return false;
    }

    private void clearMazeWithRoute() {

        while (!this.solutionRoute.empty()) {
            Point currRoutePoint = this.solutionRoute.pop();
            this.simpleMaze.setCurrMazePoint(currRoutePoint.getX(), currRoutePoint.getY(), false);
        }

    }
}
