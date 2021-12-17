package controller;

public interface IMaze2dGenerator {
    Maze2d generate(int mazeSize);
    String measureAlgorithmTime(int mazeSize) throws Exception;
}
