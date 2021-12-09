package view;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import controller.*;


public class userCommands {

    //TODO: While MVCing - split to userCommands interface, userCommands abs class then mazeUserCommands
    private ArrayList<maze2d> generatedMazes = new ArrayList<>();

    // hashmap that holds all the created commands:
    private HashMap<String, Command> commands = new HashMap<>();

    public userCommands(HashMap<String, Command> commands) {
        this.commands = commands;
    }

    public void putCommand(String string, Command command) {
        commands.put(string, command);
    }

    public void clearCommands() {
        commands.clear();
    }

    public String getAllCommandNames() {
        StringBuilder retStrbld = new StringBuilder();
        for (String str: commands.keySet()) {
            retStrbld.append(str).append("\n");
        }
        return retStrbld.toString();
    }

    public void addGeneratedMazesPath(maze2d genMaze) {
        generatedMazes.add(genMaze);
    }

    public maze2d getGeneratedMaze(String desiredMazeName) {
        for (maze2d maze : generatedMazes) {
            if (maze.getMazeName().equals(desiredMazeName))
                return maze;
        }
        return generatedMazes.get(0);
    }

    public void removeGeneratedMaze(String mazeName) {
        for (maze2d maze : generatedMazes) {
            if (maze.getMazeName().equals(mazeName)) {
                generatedMazes.remove(maze);
                return;
            }
        }
    }


    public userCommands() {
        commands.put("dir", new dirCommand());
        commands.put("genmaze", new generateCommand());
        commands.put("display", new mdisplayCommand());
        commands.put("savemaze", new saveCommand());
        commands.put("loadmaze", new loadCommand());
        commands.put("mazesize", new msizeCommand());
        commands.put("filesize", new fsizeCommand());
        commands.put("exit", new exitCommand());

    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }

    public boolean isValidCommand(String providedCommand) {
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

            } else if (Files.isDirectory(Paths.get(fullPathStr))) {
                for (Path p : Files.newDirectoryStream(Paths.get(fullPathStr))) {
                    retStr.append(p.getFileName().toString());
                }
                return retStr.toString();
            }
            return "Wrong parameter provided!";
        }
    }

    // generating mazes of kind/new maze name/ maze size
    // example: genmaze simplemaze-mazeName-mazeSize
    private class generateCommand implements Command {

        @Override
        public String doCommand(String genParams) {

            ArrayList<String> genParamsLst = new ArrayList<>(Arrays.asList(genParams.split("-")));

            // validate the maze size is int:
            //TODO: REMOVE REGEX with isInt:
            if (genParamsLst.get(2).matches("-?\\d+")) {

                if (genParamsLst.get(0).equals("simplemaze")) {
                    maze2dGenerator simpleMaze2DGenerator = new simpleMaze2DGenerator();

                    // TODO: combine with the second maze kind
                    maze2d simpleMaze2D = simpleMaze2DGenerator.generate(Integer.parseInt(genParamsLst.get(2)));

                    //TODO: change sout to os maybe on MVC
                    simpleMaze2D.setMazeName(genParamsLst.get(1));
                    addGeneratedMazesPath(simpleMaze2D);
                    return genParamsLst.get(1) + " simplemaze generated!";


                } else if (genParamsLst.get(0).equals("mymaze")) {
                    maze2dGenerator myMaze2DGenerator = new myMaze2DGenerator();


                    // TODO: combine with the second maze kind:
                    maze2d myMaze2D = myMaze2DGenerator.generate(Integer.parseInt(genParamsLst.get(2)));

                    //TODO: change sout to os: maybe on MVC
                    myMaze2D.setMazeName(genParamsLst.get(1));
                    addGeneratedMazesPath(myMaze2D);
                    return genParamsLst.get(1) + " mymaze generated!";

                } else {
                    return "Wrong maze type!";
                }
            }
            return "";
        }
    }

        private class mdisplayCommand implements Command {
            @Override
            public String doCommand(String command) {

                StringBuilder retStr = new StringBuilder();
                ArrayList<String> commandLst = new ArrayList<>(Arrays.asList(command.split(" ")));

                // in case all mazes need to be shown (show all mazes names):
                boolean allMazes = commandLst.get(0).equals("mazes") || commandLst.get(0).equals("all");

                if (allMazes) {
                    for (maze2d maze : generatedMazes) {
                        retStr.append(maze.getMazeName()).append("\n");
                    }
                }

                // show specific maze (structure):
                else {
                    retStr.append(getGeneratedMaze(commandLst.get(0)));
                }
                return retStr.toString().equals("") ? "No generated mazes!" : retStr.toString();
            }
        }

        // save compressed maze binary file from already generated mazes:
        private class saveCommand implements Command {

            @Override
            public String doCommand(String mazeName) throws IOException {

                mazeCompression MC = new mazeCompression();

                String retSaveMazeStr = MC.encodeHuffmanAndSave(getGeneratedMaze(mazeName), mazeName);
                if (retSaveMazeStr.equals(mazeName + ".bin")) {
                    // remove saved maze from generatedMazesPaths:
                    removeGeneratedMaze(mazeName);

                    return retSaveMazeStr + " saved successfully!";
                }
                return "Saving maze failed!";
            }
        }

    // load compressed maze from file and add it to dynamically generatedMazesPaths
    private class loadCommand implements Command {

        @Override
        public String doCommand(String path) throws IOException {

            mazeCompression MC = new mazeCompression();
            maze2d encodedMaze2d = MC.decodeHuffmanMazeFileToMaze(path);
            generatedMazes.add(encodedMaze2d);

            return encodedMaze2d.getMazeName() + " loaded!";
        }
    }

    // get size of the maze before compression:
    private class msizeCommand implements Command {

        @Override
        public String doCommand(String mazeName) {
           maze2d d = getGeneratedMaze(mazeName);

            return "maze size before compression: " + (d.getMazeStructure()[0].length * d.getMazeStructure().length);
        }
    }

    private class fsizeCommand implements Command {

        @Override
        public String doCommand(String path) throws IOException {

            long bytes = Files.size(Path.of(path));
            return "compressed size of file: " + bytes;
        }
    }


    // NOT SURE ABOUT THIS METHOD:

    private class exitCommand implements Command {

        @Override
        public String doCommand(String path)  {

            clearCommands();
            System.exit(0);
            return "";
        }
    }




}

