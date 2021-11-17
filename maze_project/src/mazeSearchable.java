import java.awt.*;
import java.util.ArrayList;

public class mazeSearchable implements Searchable {

    maze2d searchableMaze;

    public mazeSearchable(maze2d maze) {
        this.searchableMaze = maze;
    }

    @Override
    public State getStartState() {
        return new State(this.searchableMaze.entrance.toString());
    }


    @Override
    public State getGoalState() {
        return new State(this.searchableMaze.exit.toString());
    }

    public Point stateToPoint (State state) {

        int x = Integer.parseInt(state.getStringState().substring(14).split(",")[0]
                .replace("[", "").substring(2));
        int y = Integer.parseInt(state.getStringState().substring(14).split(",")[1]
                .replace("[", "").replace("]","").substring(2));

        return new Point(x,y);
    }


    public ArrayList<State> getAllPossibleStates(State currState) {

        ArrayList<State> allPossibleStates = new ArrayList<>();

        ArrayList<Point> PossiblePointsNeighs = this.searchableMaze.getPossibleSteps(stateToPoint(currState));
        for (Point iterPoint : PossiblePointsNeighs) {
            allPossibleStates.add(new State (iterPoint.toString()));
        }
        return allPossibleStates;
    }

}
