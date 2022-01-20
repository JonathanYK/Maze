package model;

public class SolvedMaze2d {

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
