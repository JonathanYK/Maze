package model;

import java.util.ArrayList;
import java.util.Collections;

public class Solution {

    private ArrayList<State> solution;

    public ArrayList<State> setSolution(State exitPoint) {

        this.solution = new ArrayList<>();

        // Extracting the solution path from exitPoint until the entrancePoint (where getParent == null):
        while (exitPoint.getParent() != null) {
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

    public static class SolvedMaze2d {

        public Maze2d getMaze() {
            return _maze;
        }

        public void setMaze(Maze2d _maze) {
            this._maze = _maze;
        }

        public Solution getAstar() {
            return _ASTAR;
        }

        public void setAstar(Solution _ASTAR) {
            this._ASTAR = _ASTAR;
        }

        public Solution getBfs() {
            return _BFS;
        }

        public void setBfs(Solution _BFS) {
            this._BFS = _BFS;
        }

        private Maze2d _maze;
        private Solution _ASTAR;
        private Solution _BFS;

    }
}