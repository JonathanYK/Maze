package model;

import java.util.ArrayList;

public class State {

    private String stringState;
    private State parent;
    private int gCost;
    private int hCost;

    public State(String stateStr) {
        this.stringState = stateStr;
    }
    public String getStringState() {
        return this.stringState;
    }

    public void setParent(State parentState) {
        this.parent = parentState;
    }
    public State getParent() {
        return this.parent;
    }

    public int getGcost() {
        return this.gCost;
    }
    public int getHcost() {
        return this.hCost;
    }
    public int getFcost() {
        return this.gCost + this.hCost;
    }

    private ArrayList<Integer> getStatesCoordinates (State currState, State destState) {
        ArrayList<Integer> _coordinatesStates = new ArrayList<>();

        // currStateX:
        _coordinatesStates.add(Integer.parseInt(currState.getStringState().substring(14).split(",")[0]
                .replace("[", "").substring(2)));

        // currStateY:
        _coordinatesStates.add(Integer.parseInt(currState.getStringState().substring(14).split(",")[1]
                .replace("[", "").replace("]","").substring(2)));

        // destStateX:
        _coordinatesStates.add(Integer.parseInt(destState.getStringState().substring(14).split(",")[0]
                .replace("[", "").substring(2)));

        // destStateY:
        _coordinatesStates.add(Integer.parseInt(destState.getStringState().substring(14).split(",")[1]
                .replace("[", "").replace("]","").substring(2)));

        return _coordinatesStates;
    }

    public void setHcost(State destState) {
        ArrayList<Integer> coordinatesStates = getStatesCoordinates(this, destState);
        this.hCost = (coordinatesStates.get(2) - coordinatesStates.get(0)) + (coordinatesStates.get(3) - coordinatesStates.get(1));
    }

    public void setGcost(State destState) {
        ArrayList<Integer> coordinatesStates = getStatesCoordinates(this, destState);
        this.hCost = (coordinatesStates.get(0) - coordinatesStates.get(2)) + (coordinatesStates.get(1) - coordinatesStates.get(3));
    }
}