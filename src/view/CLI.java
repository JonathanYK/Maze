package view;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Scanner;

public class CLI {

    InputStream is;
    OutputStream os;

    // default constructor with default IOstreanms (console IO):
    public CLI() {
        this.is = System.in;
        this.os = System.out;
    }


    public CLI(String inputFileStr, String outputFileStr) throws Exception {

        File initialFile = new File(inputFileStr);
        this.is = new FileInputStream(initialFile);

        File osFile = new File(outputFileStr);

        // check if concat or rewrite the file:
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



    }


    @SuppressWarnings("InfiniteLoopStatement")
    public void start(MazeIUserCommands uc) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(this.is);

        os.write("-------------------------".getBytes(StandardCharsets.UTF_8));
        os.write("\nWELCOME TO MAZES CLI\n\n".getBytes(StandardCharsets.UTF_8));
        os.write("Available commands:\n".getBytes(StandardCharsets.UTF_8));
        os.write(uc.getAllCommandNames().getBytes(StandardCharsets.UTF_8));
        os.write("-------------------------\n".getBytes(StandardCharsets.UTF_8));


        while (true) {

            String cmd = sc.next();
            String params = sc.next();
            String retStr;


            if (uc.isValidCommand(cmd)) {
                retStr = uc.getCommand(cmd).doCommand(params);
                os.write((retStr +"\n").getBytes(StandardCharsets.UTF_8));
            }
            else {
                os.write(("Wrong command provided!").getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
