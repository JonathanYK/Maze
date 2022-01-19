package controller;

import model.MazeModel;
import view.CLI;

import java.io.IOException;

public class MazeController implements IController {

    private CLI cli_view;
    private MazeModel maze_model;


    public Observable VCObservable;
    public Observer CVObserver;

    public Observable CMObservable;
    public Observer MCObserver;

    public Observable MCObservable;
    public Observer CMObserver;

    public Observable CVObservable;
    public Observer VCObserver;


    public MazeController(CLI cli_view, MazeModel maze_model) {
        this.cli_view = cli_view;
        this.maze_model = maze_model;


        this.VCObservable = new Observable();
        this.CVObserver = new Observer();
        VCObservable.add(CVObserver);

        this.CMObservable = new Observable();
        this.MCObserver = new Observer();
        CMObservable.add(MCObserver);

        this.MCObservable = new Observable();
        this.CMObserver = new Observer();
        MCObservable.add(CMObserver);

        this.CVObservable = new Observable();
        this.VCObserver = new Observer();
        CVObservable.add(VCObserver);

    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void mainMazeCommandsMenu() throws IOException, ClassNotFoundException {

        while(true) {

            // Retrieving data from view (CLI command):
            this.cli_view.retrieveViewData();

            // Transferring the data from view to the model, then validating the data:
            this.CMObservable.setData(this.CVObserver.getData());

            if (this.maze_model.validateRetrievedCommand()) {

                // transferring the validated data from the controller to the model, then executing the command:
                this.CMObservable.setData(this.CMObserver.getData());
                this.maze_model.executeCommand();
            }

            // Transferring data from the model to the view, then handling the message on view:
            this.CVObservable.setData(this.CMObserver.getData());
            this.cli_view.showMessage();

        }
    }
}
