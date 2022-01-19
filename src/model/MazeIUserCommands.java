package model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MazeIUserCommands extends IUserCommandsAbc {

    public MazeIUserCommands() {
        super();
        this.putCommand("dir", new dirICommand());
        this.putCommand("genmaze", new generateICommand());
        this.putCommand("display", new displayICommand());
        this.putCommand("savemaze", new saveICommand());
        this.putCommand("loadmaze", new loadICommand());
        this.putCommand("mazesize", new mazeSizeICommand());
        this.putCommand("filesize", new fileSizeICommand());
        this.putCommand("exit", new exitICommand());
    }

    @Override
    public String validateCommand(String[] inputBuf) {

        // Special case, where the command is exit:
        if (inputBuf[0].equalsIgnoreCase("exit"))
            inputBuf = new String [] {"exit", "e"};

        // Wrong input:
        else if (inputBuf.length != 2)
            return "Wrong input!\n";

        // Wrong command name:
        else if (!isValidCommand(inputBuf[0]))
            return "Wrong command typed!\n";

        // In case the command is correct:
        return inputBuf[0] + " " + inputBuf[1];
    }


    // this method will print full path of the file in case file name provided as path, otherwise return all the files
    // in path:
    private class dirICommand implements ICommand {
        @Override
        public void doCommand(String path) throws IOException {

            StringBuilder retStr = new StringBuilder();
            String fullPathStr = System.getProperty("user.dir");

            if (!path.equals(".")) {
                fullPathStr = System.getProperty("user.dir") + "\\" + path;
            }

            if (Files.isRegularFile(Paths.get(fullPathStr))) {
                retStr.append(fullPathStr).append("\n");

            } else if (Files.isDirectory(Paths.get(fullPathStr))) {
                for (Path p : Files.newDirectoryStream(Paths.get(fullPathStr))) {
                    // Case the path isn't a dir:
                    if(!Files.isDirectory(Paths.get(p.getFileName().toString())) && Files.isRegularFile(p)) {

                        // Saving only .bin files:
                        String fileName = p.getFileName().toString();
                        if (fileName.substring(p.getFileName().toString().lastIndexOf(".")).equals(".bin"))
                            retStr.append(fileName).append("\n");
                    }
                }
            }

            String retValidation = validateParams(retStr.toString());

            if (retValidation.equals(""))
                maze_model.mazeController.MCObservable.setData(retStr.toString());
            else
                maze_model.mazeController.MCObservable.setData(retValidation);

        }
        @Override
        public String validateParams(String param) {
            if (!param.equals(""))
                return param;
            return "Wrong parameter provided!\n";
        }
    }

    // generating mazes: kind-mazename-mazesize
    // example: genmaze simplemaze-my_new_maze-15
    private class generateICommand implements ICommand {

        @Override
        public void doCommand(String genParams) {


            String retValidation = validateParams(genParams);

            if(!retValidation.equals("")) {
                maze_model.mazeController.MCObservable.setData(retValidation);
                return;
            }
            ArrayList<String> genParamsLst = new ArrayList<>(Arrays.asList(genParams.split("-")));

            Maze2d retMaze2d = maze_model.generateMaze(genParamsLst.get(0), genParamsLst.get(1), Integer.parseInt(genParamsLst.get(2)));

            if (genMaze2D != null) {
                genMaze2D.setMazeName(mazeName);
                addGeneratedMazesPath(genMaze2D);
                mazeController.MCObservable.setData(mazeName + " " + mazeType + " with size of " + mazeSize + " generated!\n");
            }
        }

        public Maze2d generateMaze(String mazeType, String mazeName, int mazeSize) {

            IMaze2dGenerator imaze2DGenerator = null;

            if (mazeType.equals("simplemaze")) {
                imaze2DGenerator = new SimpleIIMaze2DGenerator();
            }
            else if (mazeType.equals("mymaze")) {
                imaze2DGenerator = new MyIIMaze2DGenerator();
            }

            return imaze2DGenerator.generate(mazeSize);

        }

        @Override
        public String validateParams(String param) {
            ArrayList<String> genParamsLst = new ArrayList<>(Arrays.asList(param.split("-")));
            try {
                Integer.parseInt(genParamsLst.get(2));
            } catch (NumberFormatException nfe) {
                return "genmaze received not a number value, aborted!\n";
            }

            if (!genParamsLst.get(0).equals("simplemaze") && !genParamsLst.get(0).equals("mymaze")) {
                return "Wrong maze type provided!\n";
            }

            return "";
        }
    }

        private class displayICommand implements ICommand {
            @Override
            public void doCommand(String param) {

                StringBuilder retStr = new StringBuilder();
                String retValidation = validateParams(param);

                // in case all mazes need to be shown (show all mazes names):
                if (retValidation.equals("all")) {
                    for (Maze2d maze : maze_model.getAllGeneratedMazes()) {
                        retStr.append(maze.getMazeName()).append("\n");
                    }
                }

                // show specific maze (structure):
                else {
                    retStr.append(maze_model.getGeneratedMaze(param));
                }

                if(retStr.toString().equals("null"))
                    maze_model.mazeController.MCObservable.setData("maze " + param + " not found!\n");
                else if(retStr.toString().equals(""))
                    maze_model.mazeController.MCObservable.setData("No generated mazes!\n");
                else
                    maze_model.mazeController.MCObservable.setData(retStr.toString());
            }

            @Override
            public String validateParams(String param) {
                if (param.equals("mazes") || param.equals("all"))
                    return "all";
                return "";
            }
        }

        // save compressed maze binary file from already generated mazes:
        private class saveICommand implements ICommand {

            @Override
            public void doCommand(String mazeName) throws IOException {
                String retValidation = validateParams(mazeName);

                if (!retValidation.equals("")) {
                    maze_model.mazeController.MCObservable.setData(retValidation);
                    return;
                }
                maze_model.saveMaze(mazeName);
            }

            @Override
            public String validateParams(String param) {
                Maze2d dynamicMaze = maze_model.getGeneratedMaze(param);
                if (dynamicMaze != null)
                    return "";

                return "No such dynamic maze!\n";
            }
        }

    // load compressed maze from file and add it to dynamically generatedMazesPaths
    private class loadICommand implements ICommand {

        @Override
        public void doCommand(String path) throws IOException {

            String retValidation = validateParams(path);
            if (!retValidation.equals("")) {
                maze_model.mazeController.MCObservable.setData(retValidation);
                return;
            }
            maze_model.loadMaze(path);
        }

        @Override
        public String validateParams(String param) {

            if (!Files.isRegularFile(Path.of(param)))
                return param + " is not a valid file!\n";
            return "";
        }
    }

    // get size of the maze before compression:
    private class mazeSizeICommand implements ICommand {

        @Override
        public void doCommand(String mazeName) {
           Maze2d mSizeMaze = maze_model.getGeneratedMaze(mazeName);

            String retValidation = validateParams(mazeName);

            if (!retValidation.equals("")) {
                maze_model.mazeController.MCObservable.setData("maze size before compression: " +
                        (mSizeMaze.getMazeStructure()[0].length * mSizeMaze.getMazeStructure().length) + "\n");
            }
           else
               maze_model.mazeController.MCObservable.setData(retValidation);
        }

        @Override
        public String validateParams(String param) {

            Maze2d d = maze_model.getGeneratedMaze(param);
            if (d != null)
                return "";
            return "This maze name wasn't located on the dynamic mazes!\n";
        }
    }

    private class fileSizeICommand implements ICommand {

        @Override
        public void doCommand(String path) throws IOException {

            String retValidation = validateParams(path);

            if (retValidation.equals("")) {
                long bytes = Files.size(Path.of(path));
                maze_model.mazeController.MCObservable.setData("compressed size of file: " + bytes + "\n");
            }
            else
                maze_model.mazeController.MCObservable.setData(retValidation);
        }

        @Override
        public String validateParams(String param) {
            if (Files.isRegularFile(Path.of(param)))
                return "";
            return "This filename wasn't found!\n";
        }
    }

    private class exitICommand implements ICommand {

        @Override
        public void doCommand(String path) {
            clearCommands();
            System.exit(0);
        }

        // No params in exitCommand, so in this case this validation isn't needed:
        @Override
        public String validateParams(String param) {
            return "";
        }
    }

    public boolean isValidCommand(String providedCommand) {
        return this.commands.containsKey(providedCommand);
    }

}

