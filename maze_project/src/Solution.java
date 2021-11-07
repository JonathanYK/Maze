import java.util.ArrayList;

public class Solution {



            // solution arraylist should be changed accordint to abstraction that will be created:
    protected void getFullRoute(ArrayList solution, mazePoint exitPoint) {
        while (exitPoint.getParent() != null) {
                // should handle type of exitPoint:
                solution.add(exitPoint);
                exitPoint = exitPoint.getParent();
            }
        }
    }

