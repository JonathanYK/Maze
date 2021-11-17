import java.util.ArrayList;
import java.util.Collections;

public class Solution {

    private ArrayList<State> solution;

    public ArrayList<State> setSolution(State exitPoint) {

        this.solution = new ArrayList<>();
        while (exitPoint.getParent() != null) {
            // should handle type of exitPoint:
            this.solution.add(exitPoint);
            exitPoint = exitPoint.getParent();
        }
        return this.solution;
    }

    public String getSolution() {

        final StringBuilder sb = new StringBuilder();

        Collections.reverse(this.solution);
        while (!this.solution.isEmpty()) {
            sb.append(this.solution.remove(0).getStringState().substring(14));
            if (!this.solution.isEmpty())
                sb.append(" -> ");
        }
        return sb.toString();
    }
}

