import java.util.ArrayList;

public interface Searchable {
    State getStartState();
    State getGoalState();
    ArrayList<State> getAllPossibleStates(State currState);
}
