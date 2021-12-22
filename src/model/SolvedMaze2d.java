package model;

public class SolvedMaze2d {

    public Maze2d get_maze() {
        return _maze;
    }

    public void set_maze(Maze2d _maze) {
        this._maze = _maze;
    }

    public Solution get_ASTAR() {
        return _ASTAR;
    }

    public void set_ASTAR(Solution _ASTAR) {
        this._ASTAR = _ASTAR;
    }

    public Solution get_BFS() {
        return _BFS;
    }

    public void set_BFS(Solution _BFS) {
        this._BFS = _BFS;
    }

    private Maze2d _maze;
    private Solution _ASTAR;
    private Solution _BFS;

}
