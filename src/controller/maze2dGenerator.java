package controller;

public interface maze2dGenerator {
    maze2d generate(int mazeSize);
    String measureAlgorithmTime(int mazeSize) throws Exception;
}
