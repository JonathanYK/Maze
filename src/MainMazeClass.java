import controller.MazeController;
import model.MazeModel;
import view.CLI;

// Main class that initiating the main application:
public class MainMazeClass {

    public static void main(String[] args) throws Exception {

        // Model:
        MazeModel mazeModel = new MazeModel();

        // View:
        CLI mazeCli = new CLI();
        // Instead of using CLI, we can use is.txt as input commands and os.txt as output:
        // CLI _cli = new CLI("is.txt", "os.txt", mazeModel);

        // Controller:
        MazeController mazeController = new MazeController(mazeCli, mazeModel);
        mazeCli.setController(mazeController);
        mazeModel.setController(mazeController);

        // Main maze menu initiation:
        mazeController.mainMazeCommandsMenu();

    }
}