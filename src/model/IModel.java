package model;

import controller.MazeController;
import java.io.IOException;

public interface IModel {

    void setController(MazeController controller);
    boolean validateRetrievedCommand();

    void executeCommand() throws ClassNotFoundException, IOException;
}
