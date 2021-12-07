package view;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CLI {

    InputStream is;
    OutputStream os;
    Scanner sc;

    String isStr;
    String osStr;


    // default constructor with default IOstreanms (console IO):
    public CLI() {
        this.is = System.in;
        this.os = System.out;
    }


    public CLI(InputStream is, OutputStream os) {

        this.os = os;
        this.is = is;
        sc = new Scanner(is, StandardCharsets.UTF_8.name());


        // in case the inputStream is a path to a file and not a command:
        if (!isStr.contains("\n") && !isStr.contains(" ") && Files.exists(Paths.get(isStr))) {

            //TODO: Catch the exception of Files.exists instead of above if
            System.out.println("commands from path!");
        }


        // any other inputstreams should be defined by another if's..


    }


    @SuppressWarnings("InfiniteLoopStatement")
    public void start(userCommands uc) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(this.is);


        while (true) {

            String cmd = sc.next();
            String params = sc.next();
            String retStr = "";

            if (uc.isValidCommand(cmd)) {
                retStr = uc.getCommand(cmd).doCommand(params);
                System.out.println(retStr);
            }
            else {
                System.out.println("Wrong command provided!");
            }

        }
    }
}
