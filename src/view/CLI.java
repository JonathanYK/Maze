package view;

import controller.MazeIUserCommands;
import model.MazeModel;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI {

    InputStream is;
    OutputStream os;

    MazeModel model;
    MazeIUserCommands uc;

    boolean welcomeLogoPrinted = false;


    //dbg remove:-------------
    String input = "";

    public void setCliInput(String str) {
        this.input = input.concat(str);
    }

    public String getCliInput() {
        return this.input;
    }
    //-----------------------

    // default constructor with default IOstreams (console IO):
    public CLI(MazeModel model) {
        this.is = System.in;
        this.os = System.out;

        this.model = model;
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
                System.out.println("Wrong input! exit without any actions..");
                System.exit(0);
            }
        }

        this.model = model;
        this.uc = new MazeIUserCommands();
    }


    @SuppressWarnings("InfiniteLoopStatement")
    public void start() throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(this.is);

        os.write("-------------------------".getBytes(StandardCharsets.UTF_8));
        os.write("\nWELCOME TO MAZES WORLD!\n\n".getBytes(StandardCharsets.UTF_8));
        os.write("Available commands:\n".getBytes(StandardCharsets.UTF_8));
        os.write(this.uc.getAllCommandNames().getBytes(StandardCharsets.UTF_8));
        os.write("-------------------------\n".getBytes(StandardCharsets.UTF_8));

        while (true) {

            String cmd = sc.next();
            String params = sc.next();
            String retStr;

            if (this.uc.isValidCommand(cmd)) {
                retStr = this.uc.getCommand(cmd).doCommand(params);
                this.os.write((retStr +"\n").getBytes(StandardCharsets.UTF_8));
            }
            else {
                this.os.write(("Wrong command provided!").getBytes(StandardCharsets.UTF_8));
            }
        }
    }


    private void printWelcomeLogo() throws IOException {
        this.os.write("-------------------------".getBytes(StandardCharsets.UTF_8));
        this.os.write("\nWELCOME TO MAZES WORLD!\n\n".getBytes(StandardCharsets.UTF_8));
        this.os.write("Available commands:\n".getBytes(StandardCharsets.UTF_8));
        this.os.write(this.uc.getAllCommandNames().getBytes(StandardCharsets.UTF_8));
        this.os.write("-------------------------\n".getBytes(StandardCharsets.UTF_8));

        this.welcomeLogoPrinted = true;
    }


    @SuppressWarnings("InfiniteLoopStatement")
    public List<String> getData() throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(this.is);

        if(!this.welcomeLogoPrinted)
            printWelcomeLogo();

        List<String> inputTxt = new ArrayList<>();

        inputTxt.add(sc.next());
        inputTxt.add(sc.next());

        return inputTxt;

//            if (this.uc.isValidCommand(cmd)) {
//                retStr = this.uc.getCommand(cmd).doCommand(params);
//                os.write((retStr + "\n").getBytes(StandardCharsets.UTF_8));
//            } else {
//                os.write(("Wrong command provided!").getBytes(StandardCharsets.UTF_8));
//            }
    }

    public void printer(String outputTxt) throws IOException {
        os.write((outputTxt).getBytes(StandardCharsets.UTF_8));

    }
}
