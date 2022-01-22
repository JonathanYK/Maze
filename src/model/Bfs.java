package model;

import java.util.*;

public class Bfs extends CommonISearcher {

    public Solution search(ISearchable ISearchable) {

        // Shortest (_solution) state path:
        Solution _solution = new Solution();

        // Queue that will store all available states:
        Queue<State> _availavleStateQueue = new LinkedList<>();

        // Save all visited states - in order to filter next available states:
        ArrayList<State> _visitedStates = new ArrayList<>();

        // Adding the start state to _availavleStateQueue:
        _availavleStateQueue.add(ISearchable.getStartState());
        // Evaluate on every state move:
        this.evaluated();

        while (!_availavleStateQueue.isEmpty()) {

            // Pulling the current state:
            State currState = _availavleStateQueue.remove();

            // Add currState to _visitedStates:
            _visitedStates.add(currState);

            // Breaking rule, shortest route to exit state found:
            if (currState.getStringState().equals(ISearchable.getGoalState().getStringState())) {
                _solution.setSolution(currState);
                return _solution;
            }

            // Getting all possible states:
            ArrayList<State> possibleStates = ISearchable.getAllPossibleStates(currState);

            // Iterate on all possible states, searching unvisited states:
            for (State posState : possibleStates) {

                // For all unvisited states yet, set parent and add to _availavleStateQueue:
                if (!isStateAlreadyInArraylist(posState, _visitedStates)) {
                    posState.setParent(currState);

                    if (!isStateAlreadyInArraylist(posState, _availavleStateQueue))
                        _availavleStateQueue.add(posState);

                    // Evaluate on every state move:
                    this.evaluated();
                }
            }
        }
        return _solution;
    }
}

