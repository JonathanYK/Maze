import java.util.ArrayList;

public class ASTAR extends CommonSearcher {

    public Solution search(Searchable searchable) {

        // shortest (solution) state path:
        Solution solution = new Solution();

        // ArrayList that holds the discovered states:
        ArrayList<State> StatesOpenedPool = new ArrayList<>();

        // ArrayList that holds the already visited states:
        ArrayList<State> StatesClosedPool = new ArrayList<>();

        // Holding the entrance and exit states:
        State EntranceState = searchable.getStartState();
        State ExitState = searchable.getGoalState();

        // Calculating costs for entrance and exit states:
        //EntranceState.setGcost(EntranceState);
        EntranceState.setHcost(ExitState);

        // Adding entrance state to StatesOpenedPool:
        StatesOpenedPool.add(EntranceState);
        // evaluate on every state move:
        this.evaluated();

        while (!StatesOpenedPool.isEmpty()) {

            State currState = StatesOpenedPool.get(0);

            // finding the best next point - with the best fCost (or hCost if 4 = {State@882} there is more then one point with best fCost)
            // TODO: replace with minStarpoint method

            for (int i = 1; i <= StatesOpenedPool.size() - 1; i++) {
                if (StatesOpenedPool.get(i).getFcost() < currState.getFcost() ||
                        StatesOpenedPool.get(i).getFcost() == currState.getFcost() && StatesOpenedPool.get(i).getHcost() < currState.getHcost()) {

                    currState = StatesOpenedPool.get(i);
                }
            }

            // moving the selected best fCost state from discovered to visited pool:
            StatesOpenedPool.remove(currState);

            if (!isStateAlreadyInArraylist(currState, StatesClosedPool)) {
                StatesClosedPool.add(currState);
            }

            if (currState.getStringState().equals(ExitState.getStringState())) {
                // get the solution path from parent:

                //this.getFullRoute(solution, currState);
                solution.setSolution(currState);
                return solution;
            }

            // get all possible neighbors of currState:
            ArrayList<State> availNeighbors = searchable.getAllPossibleStates(currState);

            // iterating on all neighbors:
            for (State iterNeighborState : availNeighbors) {

                // if iterNeighbor already visited, skip it:
                if (StatesClosedPool.contains(iterNeighborState))
                    continue;

                // if we found a better path to iterNeighborState (parent with less moves) or iterNeighborState isn't discovered at all:
                if (currState.getGcost() + 1 < iterNeighborState.getGcost() || !StatesOpenedPool.contains(iterNeighborState)) {

                    // calculate iterNeighborState costs:
                    iterNeighborState.setGcost(EntranceState);
                    iterNeighborState.setHcost(ExitState);

                    // set parent of iterNeighbor
                    iterNeighborState.setParent(currState);

                    // if iterNeighborState not in discovered pool (prev if was true of first condition):
                    //if (!StatesOpenedPool.contains(iterNeighborState))

                    if (!isStateAlreadyInArraylist(iterNeighborState, StatesOpenedPool) && !isStateAlreadyInArraylist(iterNeighborState, StatesClosedPool))
                        StatesOpenedPool.add(iterNeighborState);
                        // evaluate on every state move:
                        this.evaluated();
                }
            }
        }
        return solution;
    }
}