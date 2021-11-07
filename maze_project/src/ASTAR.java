import java.util.ArrayList;

public class ASTAR extends Solution {


    public ArrayList<?> astar(maze2d maze) {

        ArrayList<?> solution = new ArrayList<>();

        // ArrayList that holds the discovered points:
        ArrayList<mazeStarPoint> mazeStarPointsPoolOpened = new ArrayList<>();

        // ArrayList that holds the already visited:
        ArrayList<mazeStarPoint> mazeStarPointsPoolClosed = new ArrayList<>();

        mazeStarPoint[][] mazeStarPoints = new mazeStarPoint[maze.mazeSize][maze.mazeSize];
        for (int i = 0; i < maze.mazeSize; i++) {
            for (int j = 0; j < maze.mazeSize; j++)
                mazeStarPoints[i][j] = new mazeStarPoint(i, j);
        }

        // Holding the entrance and exit points:
        mazeStarPoint StarEntrancePoint = mazeStarPoints[maze.getEntrance().getX()][maze.getEntrance().getY()];
        mazeStarPoint StarExitPoint = mazeStarPoints[maze.getExit().getX()][maze.getExit().getY()];

        // Calculating costs for entrance and exit points:
        StarEntrancePoint.setGcost(StarEntrancePoint);
        StarEntrancePoint.setHcost(StarExitPoint);

        // Adding entrance point to discovered points pool:
        mazeStarPointsPoolOpened.add(StarEntrancePoint);

        while (!mazeStarPointsPoolOpened.isEmpty()) {

            mazeStarPoint currStarPoint = mazeStarPointsPoolOpened.get(0);

            // finding the best next point - with the best fCost (or hCost if there is more then one point with best fCost)
            // TODO: replace with minStarpoint method
            for (int i = 1; i <= mazeStarPointsPoolOpened.size() - 1; i++) {
                if (mazeStarPointsPoolOpened.get(i).getfCost() < currStarPoint.getfCost() ||
                        mazeStarPointsPoolOpened.get(i).getfCost() == currStarPoint.getfCost() && mazeStarPointsPoolOpened.get(i).gethCost() < currStarPoint.gethCost()) {

                    currStarPoint = mazeStarPointsPoolOpened.get(i);
                }
            }

            // moving the selected best fCost point from discovered to visited pool:
            mazeStarPointsPoolOpened.remove(currStarPoint);
            mazeStarPointsPoolClosed.add(currStarPoint);


            if (currStarPoint.equals(StarExitPoint)) {
                // get the solution path from parent:
                this.getFullRoute(solution, currStarPoint);
                return solution;
            }

            // get all possible neighbors of currStarPoint:
            ArrayList<mazeStarPoint> availNeighbors = maze.getAvailableNeighbors(currStarPoint, mazeStarPoints);

            // iterating on all neighbors:
            for (mazeStarPoint iterNeighborPoint : availNeighbors) {

                // if iterNeighbor already visited, skip it:
                if (mazeStarPointsPoolClosed.contains(iterNeighborPoint))
                    continue;


                // if we found a better path to iterNeighbor (parent with less moves) or iterNeighbor isn't discovered at all:
                if (currStarPoint.getgCost() + 1 < iterNeighborPoint.getgCost() || !mazeStarPointsPoolOpened.contains(iterNeighborPoint)) {

                    // calculate iterNeighbor costs:
                    iterNeighborPoint.setGcost(currStarPoint);
                    iterNeighborPoint.setHcost(StarExitPoint);

                    // set parent of iterNeighbor
                    iterNeighborPoint.setParent(currStarPoint);

                    // if iterNeighbor not in discovered pool (prev if was true of first condition):
                    if (!mazeStarPointsPoolOpened.contains(iterNeighborPoint))
                        mazeStarPointsPoolOpened.add(iterNeighborPoint);
                }
            }
        }
        return solution;
    }
}