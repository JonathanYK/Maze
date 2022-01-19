package model;

import controller.MazeController;

import java.io.IOException;
import java.util.ArrayList;

public interface IModel {

    void setController(MazeController controller);
    boolean validateRetrievedCommand();

    void executeCommand() throws ClassNotFoundException, IOException;

    void addGeneratedMazesPath(Maze2d genMaze);
    ArrayList<Maze2d> getAllGeneratedMazes();
    void removeGeneratedMaze(String mazeName);
    void generateMaze(String mazeType, String mazeName, int mazeSize);
    void saveMaze(String mazeName) throws IOException;
    void loadMaze(String path) throws IOException;
}
