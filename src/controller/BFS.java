package controller;

import java.util.*;

public class BFS extends CommonSearcher {

    public Solution search(Searchable searchable) {

        // shortest (solution) state path:
        Solution solution = new Solution();

        // queue that will store all available states:
        Queue<State> availavleStateQueue = new LinkedList<>();

        // save all visited states - in order to filter next available states:
        ArrayList<State> visitedStates = new ArrayList<>();

        // adding the start state to availavleStateQueue:
        availavleStateQueue.add(searchable.getStartState());
        // evaluate on every state move:
        this.evaluated();

        while (!availavleStateQueue.isEmpty()) {

            // pulling the current state:
            State currState = availavleStateQueue.remove();

            // add currState to visitedStates:
            visitedStates.add(currState);

            // breaking rule, shortest route to exit state found:
            if (currState.getStringState().equals(searchable.getGoalState().getStringState())) {
                solution.setSolution(currState);
                return solution;
            }

            // getting all possible states:
            ArrayList<State> possibleStates = searchable.getAllPossibleStates(currState);

            // iterate on all possible states, searching unvisited states:
            for (State posState : possibleStates) {

                // for all unvisited states yet, set parent and add to availavleStateQueue:
                if (!isStateAlreadyInArraylist(posState, visitedStates)) {
                    posState.setParent(currState);

                    if (!isStateAlreadyInArraylist(posState, availavleStateQueue))
                        availavleStateQueue.add(posState);

                    // evaluate on every state move:
                    this.evaluated();
                }
            }
        }
        return solution;
    }
}

