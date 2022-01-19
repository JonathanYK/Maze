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

        // Special case, where the command is exit:
        if (inputBuf[0].equalsIgnoreCase("exit"))
            inputBuf = new String [] {"exit", "e"};


        // Wrong input:
        else if (inputBuf.length != 2) {
            this.mazeController.MCObservable.setData("Wrong input!\n");
            return false;
        }

        // Wrong command name:
        else if (!isValidCommand(inputBuf[0])) {
            this.mazeController.MCObservable.setData("Wrong command typed!\n");
            return false;
        }

        // In case the command is correct:
        this.mazeController.MCObservable.setData(inputBuf[0] + " " + inputBuf[1]);
        return true;
    }

    public boolean isValidCommand(String providedCommand) {
        return uc.commands.containsKey(providedCommand);
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


    public void generateMaze(String mazeType, String mazeName, int mazeSize) {

        IMaze2dGenerator imaze2DGenerator = null;

        if (mazeType.equals("simplemaze")) {
            imaze2DGenerator = new SimpleIIMaze2DGenerator();
        }
        else if (mazeType.equals("mymaze")) {
            imaze2DGenerator = new MyIIMaze2DGenerator();
        }

        Maze2d genMaze2D = imaze2DGenerator.generate(mazeSize);
        if (genMaze2D != null) {
            genMaze2D.setMazeName(mazeName);
            addGeneratedMazesPath(genMaze2D);
            mazeController.MCObservable.setData(mazeName + " " + mazeType + " with size of " + mazeSize + " generated!\n");
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
