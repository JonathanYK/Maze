package view;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CLI {

    InputStream is;
    OutputStream os;

    // default constructor with default IOstreanms (console IO):
    public CLI() {
        this.is = System.in;
        this.os = System.out;
    }


    public CLI(String inputFileStr, String outputFileStr) throws IOException {

        File initialFile = new File(inputFileStr);
        this.is = new FileInputStream(initialFile);

        File osFile = new File(outputFileStr);

        // check if concat or rewrite the file:
        boolean fileCreated = osFile.createNewFile();
        if (!fileCreated) {
            throw new IOException("Unable to create file at specified path. It already exists");
        }

        this.os = new FileOutputStream(osFile, false);

    }


    @SuppressWarnings("InfiniteLoopStatement")
    public void start(mazeUserCommands uc) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(this.is);

        os.write("-------------------------".getBytes(StandardCharsets.UTF_8));
        os.write("\nWELCOME TO MAZES CLI\n\n".getBytes(StandardCharsets.UTF_8));
        os.write("Available commands:".getBytes(StandardCharsets.UTF_8));
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
