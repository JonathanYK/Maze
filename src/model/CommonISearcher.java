package model;

import java.util.ArrayList;
import java.util.Queue;

public abstract class
CommonISearcher implements ISearcher {

    int _evaluatedNodes = 0;

    @Override
    public int getPointEvaluationAmount() {
        return this._evaluatedNodes;
    }

    public void evaluated() {
        this._evaluatedNodes++;
    }

    // Checking if curState already visited:
    protected boolean isStateAlreadyInArraylist(State curState, ArrayList<State> StatesArraylist) {

        for (State visitedState : StatesArraylist) {
            if (visitedState.getStringState().equals(curState.getStringState()))
                return true;
        }
        return false;
    }

    protected boolean isStateAlreadyInArraylist(State curState, Queue<State> queueList) {
        return isStateAlreadyInArraylist(curState,new ArrayList<>(queueList));
    }

}
