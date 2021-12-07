package controller;

import java.util.ArrayList;
import java.util.Queue;

public abstract class CommonSearcher implements Searcher {

    int evaluatedNodes = 0;

    @Override
    public int getPointEvaluationAmount() {
        return this.evaluatedNodes;
    }

    public void evaluated() {
        this.evaluatedNodes++;
    }

    // checking if curState already visited:
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
