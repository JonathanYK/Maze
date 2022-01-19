package view;

import controller.MazeController;
import model.MazeIUserCommands;
import model.MazeModel;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CLI implements IView {

    InputStream is;
    OutputStream os;

    MazeController mazeController;
    MazeIUserCommands uc;

    boolean welcomeLogoPrinted = false;


    // default constructor with default IOstreams (console IO):
    public CLI() {
        this.is = System.in;
        this.os = System.out;

        this.uc = new MazeIUserCommands();
    }

    public CLI(String inputFileStr, String outputFileStr, MazeModel model) throws Exception {
        File initialFile = new File(inputFileStr);
        this.is = new FileInputStream(initialFile);
        File osFile = new File(outputFileStr);

        // check if appending or rewriting the file:
        boolean fileCreated = osFile.createNewFile();
        if (!fileCreated) {
            Scanner sc = new Scanner(System.in);

            System.out.println("This file already exists, override? y/n");
            String ans = sc.nextLine();

            if (ans.equals("y")) {
                System.out.println("Overriding the file...");
                boolean fileDeleted = osFile.delete();
                boolean fileOverrideCreated = osFile.createNewFile();
                if(!fileDeleted || !fileOverrideCreated)
                    throw new Exception("Deleting or creating overridden file failed!");

                this.os = new FileOutputStream(osFile, false);
            }
            else if (ans.equals("n")) {
                System.out.println("Appending to the existing file...");
                this.os = new FileOutputStream(osFile, true);
            }

            else {
                System.out.println("Wrong input! aborting..");
                System.exit(0);
            }
        }
        this.uc = new MazeIUserCommands();
    }

    public void setController(MazeController controller) {
        this.mazeController = controller;
    }

    private void printWelcomeLogo() throws IOException {
        this.os.write("-------------------------".getBytes(StandardCharsets.UTF_8));
        this.os.write("\nWELCOME TO MAZES WORLD!\n\n".getBytes(StandardCharsets.UTF_8));
        this.os.write("Available commands:\n".getBytes(StandardCharsets.UTF_8));
        this.os.write(this.uc.getAllCommandNames().getBytes(StandardCharsets.UTF_8));
        this.os.write("-------------------------\n".getBytes(StandardCharsets.UTF_8));

        this.welcomeLogoPrinted = true;
    }

    public void retrieveViewData() throws IOException {
        Scanner sc = new Scanner(this.is);

        if (!this.welcomeLogoPrinted)
            printWelcomeLogo();

        this.mazeController.VCObservable.setData(sc.nextLine());
    }

    public void showMessage() throws IOException {
        os.write((this.mazeController.VCObserver.getData()).getBytes(StandardCharsets.UTF_8));
    }
}
