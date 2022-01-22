package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class SimpleIIMaze2DGenerator extends AbstractIMaze2DGenerator {

    @Override
    public Maze2d generate(int mazeSize) {
        Maze2d _maze = new Maze2d(mazeSize);

        // Set Random walls on generated _maze:
        setRandomWalls(_maze);

        // Create path from entrance to exit of the _maze:
        Stack<MazePoint> currDFSPath = createPathIteratively(_maze);

        // Clear the _maze according to the path:
        clearMazeWithRoute(_maze, currDFSPath);

        return _maze;
    }

    private void setRandomWalls(Maze2d maze) {

        Random rand = new Random();
        for (int i = 0; i< maze.getMazeSize(); i++) {
            for (int j = 0; j < maze.getMazeSize(); j++) {
                maze.setPointVal(i, j, rand.nextBoolean());
            }
        }
    }

    private Stack<MazePoint> createPathIteratively(Maze2d maze) {
        Stack<MazePoint> routesTrace = new Stack<>();
        MazePoint[][] MazePoints = new MazePoint[maze.getMazeSize()][maze.getMazeSize()];

        ArrayList<MazePoint> alreadyVisited = new ArrayList<>();

        for (int i = 0; i < maze.getMazeSize(); i++)
            for (int j = 0; j < maze.getMazeSize(); j++)
                MazePoints[i][j] = new MazePoint(i, j);

        MazePoint entranceMazePoint = MazePoints[maze.getEntrance().x][maze.getEntrance().y];

        entranceMazePoint.setVisited(true);
        routesTrace.push(entranceMazePoint);
        alreadyVisited.add(entranceMazePoint);

        while (!routesTrace.empty()) {

            MazePoint currMazePoint = routesTrace.pop();

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
                    MazePoints[possibleStep.x][possibleStep.y].setVisited(true);
                    MazePoints[possibleStep.x][possibleStep.y].setParent(currMazePoint);
                    routesTrace.push(MazePoints[possibleStep.x][possibleStep.y]);
                    alreadyVisited.add(MazePoints[possibleStep.x][possibleStep.y]);
                }
            }
        }
        return routesTrace;
    }

    // Validate the point wasn't visited
    public boolean pointAlreadyTraced(Point p, ArrayList<MazePoint> alreadyVisited) {
        for (MazePoint av : alreadyVisited)
            if (av.getX() == p.x && av.getY() == p.y)
                return false;
        return true;
    }

    private Stack<MazePoint> getSolutionPath(Maze2d maze, MazePoint revSolutionMazePoint) {
        Stack <MazePoint> currDFSPath = new Stack<>();

        // Adding the start point:
        currDFSPath.push(new MazePoint(maze.getEntrance().x, maze.getEntrance().y));

        while (revSolutionMazePoint.getParent() != null) {
            currDFSPath.push(new MazePoint(revSolutionMazePoint.getX(), revSolutionMazePoint.getY()));
            revSolutionMazePoint = revSolutionMazePoint.getParent();
        }
        return currDFSPath;
    }

    private void clearMazeWithRoute(Maze2d maze, Stack<MazePoint> currDFSPath) {

        while (!currDFSPath.empty()) {
            MazePoint currRouteMazePoint = currDFSPath.pop();
            maze.setPointVal(currRouteMazePoint.getX(), currRouteMazePoint.getY(), true);
        }
    }
}