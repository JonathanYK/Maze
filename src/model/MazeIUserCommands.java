package model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MazeIUserCommands extends IUserCommandsAbc {

    private ArrayList<Maze2d> generatedMazes = new ArrayList<>();

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

    public ArrayList<Maze2d> getAllGeneratedMazes() { return this.generatedMazes; }

    public void addGeneratedMazesPath(Maze2d genMaze) {
        generatedMazes.add(genMaze);
    }
    public Maze2d getGeneratedMaze(String desiredMazeName) {
        for (Maze2d maze : generatedMazes) {
            if (maze.getMazeName().equals(desiredMazeName))
                return maze;
        }
        return null;
    }

    public void removeGeneratedMaze(String mazeName) {
        for (Maze2d maze : generatedMazes) {
            if (maze.getMazeName().equals(mazeName)) {
                generatedMazes.remove(maze);
                return;
            }
        }
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
        public String doCommand(String path) throws IOException {

            StringBuilder retStr = new StringBuilder();
            String fullPathStr = System.getProperty("user.dir");

            if (!path.equals("."))
                fullPathStr = System.getProperty("user.dir") + "\\" + path;

            if (Files.isRegularFile(Paths.get(fullPathStr)))
                retStr.append(fullPathStr).append("\n");

            else if (Files.isDirectory(Paths.get(fullPathStr))) {
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
               retValidation = retStr.toString();
            return retValidation;

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
        public String doCommand(String genParams) {

            String retValidation = validateParams(genParams);

            if(!retValidation.equals(""))
                return retValidation;

            ArrayList<String> genParamsLst = new ArrayList<>(Arrays.asList(genParams.split("-")));
            Maze2d retMaze2d = generateMaze(genParamsLst.get(0), Integer.parseInt(genParamsLst.get(2)));

            if (retMaze2d != null) {
                retMaze2d.setMazeName(genParamsLst.get(1));
                addGeneratedMazesPath(retMaze2d);

                return genParamsLst.get(1) + " " + genParamsLst.get(0) +
                        " with size of " + genParamsLst.get(2) + " generated!\n";
            }
            return "Generating maze failed!\n";
        }

        public Maze2d generateMaze(String mazeType, int mazeSize) {

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

            if (!genParamsLst.get(0).equals("simplemaze") && !genParamsLst.get(0).equals("mymaze"))
                return "Wrong maze type provided!\n";

            return "";
        }
    }

        private class displayICommand implements ICommand {
            @Override
            public String doCommand(String param) {

                StringBuilder retStr = new StringBuilder();
                String retValidation = validateParams(param);

                // in case all mazes need to be shown (show all mazes names):
                if (retValidation.equals("all")) {
                    for (Maze2d maze : getAllGeneratedMazes()) {
                        retStr.append(maze.getMazeName()).append("\n");
                    }
                }

                // show specific maze (structure):
                else
                    retStr.append(getGeneratedMaze(param));

                if(retStr.toString().equals("null"))
                    return "maze " + param + " not found!\n";
                else if(retStr.toString().equals(""))
                    return "No generated mazes!\n";
                else
                    return retStr.toString();
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
            public String doCommand(String mazeName) throws IOException {
                String retValidation = validateParams(mazeName);

                if (!retValidation.equals(""))
                    return retValidation;
                return saveMaze(mazeName);
            }


            public String saveMaze(String mazeName) throws IOException {

                MazeCompression MC = new MazeCompression();

                String retSaveMazeStr = MC.encodeHuffmanAndSave(getGeneratedMaze(mazeName), mazeName);
                if (retSaveMazeStr.equals(mazeName + ".bin")) {

                    // remove saved maze from generatedMazesPaths:
                    removeGeneratedMaze(mazeName);
                   return retSaveMazeStr + " saved successfully!\n";
                }
                return "Saving maze failed!\n";
            }

            @Override
            public String validateParams(String param) {
                Maze2d dynamicMaze = getGeneratedMaze(param);
                if (dynamicMaze != null)
                    return "";
                return "No such dynamic maze!\n";
            }
        }

    // load compressed maze from file and add it to dynamically generatedMazesPaths
    private class loadICommand implements ICommand {

        @Override
        public String doCommand(String path) throws IOException {

            String retValidation = validateParams(path);
            if (!retValidation.equals("")) {
                return retValidation;
            }
            return loadMaze(path);
        }

        @Override
        public String validateParams(String param) {

            if (!Files.isRegularFile(Path.of(param)))
                return param + " is not a valid file!\n";
            return "";
        }

        public String loadMaze(String path) throws IOException {

            MazeCompression MC = new MazeCompression();

            Maze2d encodedMaze2D = MC.decodeHuffmanMazeFileToMaze(path);
            addGeneratedMazesPath(encodedMaze2D);

            return encodedMaze2D.getMazeName() + " loaded!\n";
        }

    }

    // get size of the maze before compression:
    private class mazeSizeICommand implements ICommand {

        @Override
        public String doCommand(String mazeName) {
           Maze2d mSizeMaze = getGeneratedMaze(mazeName);

            String retValidation = validateParams(mazeName);

            if (!retValidation.equals("")) {
                return "maze size before compression: " +
                        (mSizeMaze.getMazeStructure()[0].length * mSizeMaze.getMazeStructure().length) + "\n";
            }
           else
               return retValidation;
        }

        @Override
        public String validateParams(String param) {

            Maze2d d = getGeneratedMaze(param);
            if (d != null)
                return "";
            return "This maze name wasn't located on the dynamic mazes!\n";
        }
    }

    private class fileSizeICommand implements ICommand {

        @Override
        public String doCommand(String path) throws IOException {

            String retValidation = validateParams(path);

            if (retValidation.equals("")) {
                long bytes = Files.size(Path.of(path));
                return "compressed size of file: " + bytes + "\n";
            }
            else
                return retValidation;
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
        public String doCommand(String path) {
            clearCommands();
            System.exit(0);

            return "";
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