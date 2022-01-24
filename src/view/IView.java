package view;

import controller.MazeController;
import java.io.IOException;

public interface IView {
    void setController(MazeController controller);
    void retrieveViewData() throws IOException;
    void showMessage() throws IOException;
}
