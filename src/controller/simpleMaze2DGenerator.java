package controller;

import java.awt.*;
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

        // create path from entrance to exit of the maze:
        Stack<mazePoint> currDFSPath = createPathIteratively(maze);

        // clear the maze according to the path:
        clearMazeWithRoute(maze, currDFSPath);

        return maze;
    }

    private void setRandomWalls(maze2d maze) {

        Random rand = new Random();
        for (int i = 0; i< maze.getMazeSize(); i++) {
            for (int j = 0; j < maze.getMazeSize(); j++) {
                maze.setPointVal(i, j, rand.nextBoolean());
            }
        }
    }


    private Stack<mazePoint> createPathIteratively(maze2d maze) {
        Stack<mazePoint> routesTrace = new Stack<>();
        mazePoint[][] mazePoints = new mazePoint[maze.getMazeSize()][maze.getMazeSize()];

        ArrayList<mazePoint> alreadyVisited = new ArrayList<>();

        for (int i = 0; i < maze.getMazeSize(); i++)
            for (int j = 0; j < maze.getMazeSize(); j++)
                mazePoints[i][j] = new mazePoint(i, j);

        mazePoint entranceMazePoint = mazePoints[maze.getEntrance().x][maze.getEntrance().y];

        entranceMazePoint.setVisited(true);
        routesTrace.push(entranceMazePoint);
        alreadyVisited.add(entranceMazePoint);

        while (!routesTrace.empty()) {

            mazePoint currMazePoint = routesTrace.pop();

            if (currMazePoint.getX() == maze.getExit().x && currMazePoint.getY() == maze.getExit().y) {
                routesTrace.push(currMazePoint);
                return getSolutionPath(maze, routesTrace.pop());
            }

            // Getting all available neighbors:
            ArrayList<Point> possibleSteps = maze.getPossibleSteps(new Point(currMazePoint.getX(), currMazePoint.getY()), true);

            // Choose next neighbor randomly in order to create different paths on each iteration:
            Collections.shuffle(possibleSteps);

            for (Point possibleStep: possibleSteps) {
                boolean addTrace = pointAlreadyTraced(possibleStep, alreadyVisited);
                if (addTrace) {
                    mazePoints[possibleStep.x][possibleStep.y].setVisited(true);
                    mazePoints[possibleStep.x][possibleStep.y].setParent(currMazePoint);
                    routesTrace.push(mazePoints[possibleStep.x][possibleStep.y]);

                    alreadyVisited.add(mazePoints[possibleStep.x][possibleStep.y]);
                }
            }
        }
        return routesTrace;
    }

    // validate the point wasn't visited
    public boolean pointAlreadyTraced(Point p, ArrayList<mazePoint> alreadyVisited) {
        for (mazePoint av : alreadyVisited)
            if (av.getX() == p.x && av.getY() == p.y)
                return false;
        return true;
    }

    private Stack<mazePoint> getSolutionPath(maze2d maze, mazePoint revSolutionMazePoint) {
        Stack <mazePoint> currDFSPath = new Stack<>();

        // Adding the start point:
        currDFSPath.push(new mazePoint(maze.getEntrance().x, maze.getEntrance().y));

        while (revSolutionMazePoint.getParent() != null) {
            currDFSPath.push(new mazePoint(revSolutionMazePoint.getX(), revSolutionMazePoint.getY()));
            revSolutionMazePoint = revSolutionMazePoint.getParent();
        }
        return currDFSPath;
    }

    private void clearMazeWithRoute(maze2d maze, Stack<mazePoint> currDFSPath) {

        while (!currDFSPath.empty()) {
            mazePoint currRouteMazePoint = currDFSPath.pop();
            maze.setPointVal(currRouteMazePoint.getX(), currRouteMazePoint.getY(), true);
        }
    }
}
