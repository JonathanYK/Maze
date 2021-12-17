package controller;

import java.util.ArrayList;

public interface ISearchable {
    State getStartState();
    State getGoalState();
    ArrayList<State> getAllPossibleStates(State currState);
}
