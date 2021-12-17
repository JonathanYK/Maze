package controller;

import java.util.*;

public class BFS extends CommonISearcher {

    public Solution search(ISearchable ISearchable) {

        // shortest (_solution) state path:
        Solution _solution = new Solution();

        // queue that will store all available states:
        Queue<State> _availavleStateQueue = new LinkedList<>();

        // save all visited states - in order to filter next available states:
        ArrayList<State> _visitedStates = new ArrayList<>();

        // adding the start state to _availavleStateQueue:
        _availavleStateQueue.add(ISearchable.getStartState());
        // evaluate on every state move:
        this.evaluated();

        while (!_availavleStateQueue.isEmpty()) {

            // pulling the current state:
            State currState = _availavleStateQueue.remove();

            // add currState to _visitedStates:
            _visitedStates.add(currState);

            // breaking rule, shortest route to exit state found:
            if (currState.getStringState().equals(ISearchable.getGoalState().getStringState())) {
                _solution.setSolution(currState);
                return _solution;
            }

            // getting all possible states:
            ArrayList<State> possibleStates = ISearchable.getAllPossibleStates(currState);

            // iterate on all possible states, searching unvisited states:
            for (State posState : possibleStates) {

                // for all unvisited states yet, set parent and add to _availavleStateQueue:
                if (!isStateAlreadyInArraylist(posState, _visitedStates)) {
                    posState.setParent(currState);

                    if (!isStateAlreadyInArraylist(posState, _availavleStateQueue))
                        _availavleStateQueue.add(posState);

                    // evaluate on every state move:
                    this.evaluated();
                }
            }
        }
        return _solution;
    }
}

