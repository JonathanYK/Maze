package controller;

import model.MazeModel;
import view.CLI;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class MazeController {

    private MazeIUserCommands uc = new MazeIUserCommands();

    private CLI cli_view;
    private MazeModel maze_model;

    public MazeController(CLI cli_view, MazeModel maze_model) {
        this.cli_view = cli_view;
        this.maze_model = maze_model;
    }


    public void controllerStarted() throws IOException, ClassNotFoundException {

        Observable observable = new Observable();
        Observer observer = new Observer();
        observable.add(observer);


        while(true) {
            List<String> retData = this.cli_view.getData();

            if (this.uc.isValidCommand(retData.get(0))) {
                String retStr = this.uc.getCommand(retData.get(0)).doCommand(retData.get(1));

                this.cli_view.printer(retStr + "\n");
            } else {
                this.cli_view.printer("Wrong command provided!");
            }

        }







//        this.cli_view.setCliInput("generate maze size 5");
//
//        observable.setData(this.cli_view.getCliInput());
//
//        String retDataExample = observer.data;
//        System.out.println("hh");




    }
}
