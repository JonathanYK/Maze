package model;

import controller.MazeController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MazeModel implements IModel {

    protected MazeController mazeController;
    private MazeIUserCommands uc;

    private ArrayList<String> validCommands = new ArrayList<>();

    public MazeModel() {
        this.uc = new MazeIUserCommands();
    }

    public void setController(MazeController controller) {
        this.mazeController = controller;
    }

    public boolean validateRetrievedCommand() {

        String[] inputBuf = mazeController.MCObserver.getData().split(" ");
        String retValidation = this.uc.validateCommand(inputBuf);

        mazeController.MCObservable.setData(retValidation);
        String retValidationFirstWord = retValidation.split(" ", 1)[0];

        if (retValidationFirstWord.toLowerCase(Locale.ROOT).startsWith("wrong"))
            return false;

        validCommands.add(retValidation);
        return true;
    }

    public void executeCommand() throws ClassNotFoundException, IOException {

        List<String> cmdData = new ArrayList<>(Arrays.asList(this.validCommands.get(validCommands.size()-1).split(" ")));
        this.mazeController.MCObservable.setData(this.uc.getCommand(cmdData.get(0)).doCommand(cmdData.get(1)));
    }
}
