package model;

import controller.MazeController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MazeModel implements IModel {

    private ArrayList<Maze2d> generatedMazes = new ArrayList<>();
    protected MazeController mazeController;
    private MazeIUserCommands uc;

    public MazeModel() {

        this.uc = new MazeIUserCommands();
        this.uc.setMaze_model(this);
    }

    public void setController(MazeController controller) {
        this.mazeController = controller;
    }


    public boolean validateRetrievedCommand() {

        String[] inputBuf = mazeController.MCObserver.getData().split(" ");
        String retValidation = this.uc.validateCommand(inputBuf);

        mazeController.MCObservable.setData(retValidation);

        if (retValidation.startsWith("Wrong") || retValidation.startsWith("wrong"))
            return false;

        return true;
    }


    public void executeCommand() throws ClassNotFoundException, IOException {

        List<String> cmdData = new ArrayList<>(Arrays.asList(this.mazeController.MCObserver.getData().split(" ")));
        this.uc.getCommand(cmdData.get(0)).doCommand(cmdData.get(1));
    }


    public void addGeneratedMazesPath(Maze2d genMaze) {
        this.generatedMazes.add(genMaze);
    }

    public ArrayList<Maze2d> getAllGeneratedMazes() { return this.generatedMazes; }

    public Maze2d getGeneratedMaze(String desiredMazeName) {
        for (Maze2d maze : generatedMazes) {
            if (maze.getMazeName().equals(desiredMazeName))
                return maze;
        }
        return null;
    }

    public void removeGeneratedMaze(String mazeName) {
        for (Maze2d maze : generatedMazes) {
            if (maze.getMazeName().equals(mazeName)) {
                generatedMazes.remove(maze);
                return;
            }
        }
    }





    public void saveMaze(String mazeName) throws IOException {

        MazeCompression MC = new MazeCompression();

        String retSaveMazeStr = MC.encodeHuffmanAndSave(this.getGeneratedMaze(mazeName), mazeName);
        if (retSaveMazeStr.equals(mazeName + ".bin")) {

            // remove saved maze from generatedMazesPaths:
            this.removeGeneratedMaze(mazeName);
            mazeController.MCObservable.setData(retSaveMazeStr + " saved successfully!\n");
            return;
        }
        mazeController.MCObservable.setData("Saving maze failed!\n");
    }

    public void loadMaze(String path) throws IOException {

        MazeCompression MC = new MazeCompression();

        Maze2d encodedMaze2D = MC.decodeHuffmanMazeFileToMaze(path);
        this.addGeneratedMazesPath(encodedMaze2D);

        mazeController.MCObservable.setData(encodedMaze2D.getMazeName() + " loaded!\n");
    }
}
