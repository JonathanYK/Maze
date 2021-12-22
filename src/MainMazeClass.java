import controller.MazeController;
import model.MazeModel;
import view.CLI;


public class MainMazeClass {

    public static void main(String[] args) throws Exception {
        // Model:
        MazeModel mazeModel = new MazeModel();

        // View:
        CLI mazeCli = new CLI(mazeModel);
        // CLI _cli = new CLI("is.txt", "os.txt", mazeModel);

        // Controller:
        MazeController mazeController = new MazeController(mazeCli, mazeModel);
        mazeController.controllerStarted();

        // Adding view observer to observable model:
        //mazeModel.add(mazeCli);

        // Initiating view:
        //mazeCli.start();
    }
}