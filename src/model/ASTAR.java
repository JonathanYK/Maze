package model;

import java.util.ArrayList;

public class ASTAR extends CommonISearcher {

    public Solution search(ISearchable ISearchable) {

        // shortest (_solution) state path:
        Solution _solution = new Solution();

        // ArrayList that holds the discovered states:
        ArrayList<State> _StatesOpenedPool = new ArrayList<>();

        // ArrayList that holds the already visited states:
        ArrayList<State> _StatesClosedPool = new ArrayList<>();

        // Holding the entrance and exit states:
        State EntranceState = ISearchable.getStartState();
        State ExitState = ISearchable.getGoalState();

        // Calculating costs for entrance and exit states:
        //EntranceState.setGcost(EntranceState);
        EntranceState.setHcost(ExitState);

        // Adding entrance state to _StatesOpenedPool:
        _StatesOpenedPool.add(EntranceState);
        // evaluate on every state move:
        this.evaluated();

        while (!_StatesOpenedPool.isEmpty()) {

            State currState = _StatesOpenedPool.get(0);

            // finding the best next point - with the best fCost (or hCost if 4 = {controller.State@882} there is more then one point with best fCost)
            for (int i = 1; i <= _StatesOpenedPool.size() - 1; i++) {
                if (_StatesOpenedPool.get(i).getFcost() < currState.getFcost() ||
                        _StatesOpenedPool.get(i).getFcost() == currState.getFcost() && _StatesOpenedPool.get(i).getHcost() < currState.getHcost()) {

                    currState = _StatesOpenedPool.get(i);
                }
            }

            // moving the selected best fCost state from discovered to visited pool:
            _StatesOpenedPool.remove(currState);

            if (!isStateAlreadyInArraylist(currState, _StatesClosedPool)) {
                _StatesClosedPool.add(currState);
            }

            if (currState.getStringState().equals(ExitState.getStringState())) {
                // get the _solution path from parent:

                //this.getFullRoute(_solution, currState);
                _solution.setSolution(currState);
                return _solution;
            }

            // get all possible neighbors of currState:
            ArrayList<State> availNeighbors = ISearchable.getAllPossibleStates(currState);

            // iterating on all neighbors:
            for (State iterNeighborState : availNeighbors) {

                // if iterNeighbor already visited, skip it:
                if (_StatesClosedPool.contains(iterNeighborState))
                    continue;

                // if we found a better path to iterNeighborState (parent with less moves) or iterNeighborState isn't discovered at all:
                if (currState.getGcost() + 1 < iterNeighborState.getGcost() || !_StatesOpenedPool.contains(iterNeighborState)) {

                    // calculate iterNeighborState costs:
                    iterNeighborState.setGcost(EntranceState);
                    iterNeighborState.setHcost(ExitState);

                    // set parent of iterNeighbor
                    iterNeighborState.setParent(currState);

                    // if iterNeighborState not in discovered pool (prev if was true of first condition):
                    //if (!_StatesOpenedPool.contains(iterNeighborState))

                    if (!isStateAlreadyInArraylist(iterNeighborState, _StatesOpenedPool) && !isStateAlreadyInArraylist(iterNeighborState, _StatesClosedPool))
                        _StatesOpenedPool.add(iterNeighborState);
                        // evaluate on every state move:
                        this.evaluated();
                }
            }
        }
        return _solution;
    }
}