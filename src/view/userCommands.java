package view;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import controller.*;


public class userCommands {


    //TODO: change to arrayList:
    private ArrayList <maze2d> generatedMazesPaths = new ArrayList<>();

    // hashmap that holds all the created commands:
    private HashMap<String, Command> commands = new HashMap<>();

    public userCommands (HashMap <String, Command> commands) {
        this.commands = commands;
    }

    public void putCommand(String string, Command command) {
        commands.put(string, command);
    }
    public void clearCommands(){
        commands.clear();
    }


    public userCommands(){
        commands.put("dir", new dirCommand());
        commands.put("genmaze", new generateCommand());
        commands.put("display", new mdisplayCommand());
        commands.put("savemaze", new saveCommand());
//        commands.put("loadmaze", new loadCommand());
//        commands.put("mazesize", new msizeCommand());
//        commands.put("filesize", new fsizeCommand());
//        commands.put("solve", new solveCommand());
//        commands.put("solutiondisplay", new sdisplayCommand());
//        commands.put("exit", new exitCommand());

    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }

    public boolean isValidCommand (String providedCommand) {
        return commands.containsKey(providedCommand);
    }


    // this method will print full path of the file in case file name provided as path, otherwise return all the files
    // in provided path:
    private class dirCommand implements Command {
        @Override
        public String doCommand(String path) throws IOException {

            StringBuilder retStr = new StringBuilder();
            String fullPathStr = System.getProperty("user.dir") + "\\" + path;

            if (Files.isRegularFile(Paths.get(fullPathStr))) {
                return fullPathStr;
            }

            else if (Files.isDirectory(Paths.get(fullPathStr))) {
                for (Path p : Files.newDirectoryStream(Paths.get(fullPathStr))) {
                    retStr.append(p.getFileName().toString());
                }
                return retStr.toString();
            }
            return "Wrong parameter provided!";
        }
    }


    // example: genmaze simplemaze-mazeName-mazeSize
    private class generateCommand implements Command {

        @Override
        public String doCommand(String genParams) {

            ArrayList<String> genParamsLst = new ArrayList<>(Arrays.asList(genParams.split("-")));

            // validate the maze size is int:
            if (genParamsLst.get(2).matches("-?\\d+")) {

                if (genParamsLst.get(0).equals("simplemaze")) {
                    simpleMaze2DGenerator simpleMaze2DGenerator = new simpleMaze2DGenerator();
                    maze2d simpleMaze2D = simpleMaze2DGenerator.generate(Integer.parseInt(genParamsLst.get(2)));
                    System.out.println(genParamsLst.get(1) + " simplemaze generated!");
                    simpleMaze2D.setMazeName(genParamsLst.get(1));
                    addGeneratedMazesPath(simpleMaze2D);

                } else if (genParamsLst.get(0).equals("mymaze")) {
                    myMaze2DGenerator myMaze2DGenerator = new myMaze2DGenerator();
                    maze2d myMaze2D = myMaze2DGenerator.generate(Integer.parseInt(genParamsLst.get(2)));
                    System.out.println(genParamsLst.get(1) + " mymaze generated!");
                    myMaze2D.setMazeName(genParamsLst.get(1));
                    addGeneratedMazesPath(myMaze2D);
                }
                else {
                    System.out.println("Wrong maze type!");
                }
            }
            return "";
        }
        public void addGeneratedMazesPath(maze2d genMaze) {
            generatedMazesPaths.add(genMaze);
        }
    }


    private class mdisplayCommand implements Command {
        @Override
        public String doCommand(String command) {

            StringBuilder retStr = new StringBuilder();
            ArrayList <String> commandLst = new ArrayList<>(Arrays.asList(command.split(" ")));
            boolean allMazes = commandLst.get(0).equals("mazes") || commandLst.get(0).equals("all");

                for (maze2d maze : generatedMazesPaths) {
                    if (allMazes)
                        retStr.append(maze.getMazeName());

                    else if (commandLst.get(0).equals(maze.getMazeName()))
                        return maze.toString();
                }

            return retStr.toString().equals("") ? "No generated mazes!" : retStr.toString();
        }
    }



    private class saveCommand implements Command {

        @Override
        public String doCommand(String path) {
            return "";
        }
    }




    private class Size implements Command {

        @Override
        public String doCommand(String path) {
            BufferedInputStream in = null;
            try {
                in = new BufferedInputStream(new FileInputStream(path));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            int size=0;
            try {
                while((in.read())!=-1){
                    size++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("File size is: "+size);
            return "";
        }
    }





}
