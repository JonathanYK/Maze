package model;

import java.awt.*;
import java.util.ArrayList;

public class MazeISearchable implements ISearchable {

    Maze2d _searchableMaze;

    public MazeISearchable(Maze2d maze) {
        this._searchableMaze = maze;
    }

    @Override
    public State getStartState() {
        return new State(this._searchableMaze.entrance.toString());
    }

    @Override
    public State getGoalState() {
        return new State(this._searchableMaze.exit.toString());
    }

    public Point stateToPoint (State state) {
        int x = Integer.parseInt(state.getStringState().substring(14).split(",")[0]
                .replace("[", "").substring(2));
        int y = Integer.parseInt(state.getStringState().substring(14).split(",")[1]
                .replace("[", "").replace("]","").substring(2));

        return new Point(x,y);
    }

    public ArrayList<State> getAllPossibleStates(State currState) {

        ArrayList<State> _allPossibleStates = new ArrayList<>();
        ArrayList<Point> PossiblePointsNeighs = this._searchableMaze.getPossibleSteps(stateToPoint(currState));
        for (Point iterPoint : PossiblePointsNeighs) {
            _allPossibleStates.add(new State (iterPoint.toString()));
        }
        return _allPossibleStates;
    }
}