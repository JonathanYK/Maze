package view;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import controller.*;


public class mazeUserCommands extends userCommandsAbc {

    private ArrayList<maze2d> generatedMazes = new ArrayList<>();

    public void addGeneratedMazesPath(maze2d genMaze) {
        this.generatedMazes.add(genMaze);
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

    public mazeUserCommands() {
        super();
        this.putCommand("dir", new dirCommand());
        this.putCommand("genmaze", new generateCommand());
        this.putCommand("display", new mdisplayCommand());
        this.putCommand("savemaze", new saveCommand());
        this.putCommand("loadmaze", new loadCommand());
        this.putCommand("mazesize", new msizeCommand());
        this.putCommand("filesize", new fsizeCommand());
        this.putCommand("exit", new exitCommand());

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
            maze2dGenerator maze2DGenerator;

            // validate the maze size is int:
            try {
                Integer.parseInt(genParamsLst.get(2));
            } catch (NumberFormatException nfe) {
                return "genmaze recieved not a number value, aborted!";
            }

            if (genParamsLst.get(0).equals("simplemaze")) {
                maze2DGenerator = new simpleMaze2DGenerator();
            }

             else if (genParamsLst.get(0).equals("mymaze")) {
                maze2DGenerator = new myMaze2DGenerator();
            }

            else {
                return "Wrong maze type!";
            }

                maze2d genMaze2D = maze2DGenerator.generate(Integer.parseInt(genParamsLst.get(2)));
                genMaze2D.setMazeName(genParamsLst.get(1));
                addGeneratedMazesPath(genMaze2D);
                return genParamsLst.get(1) + " " + genParamsLst.get(0) + " generated!";

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

    private class exitCommand implements Command {

        @Override
        public String doCommand(String path)  {

            clearCommands();
            System.exit(0);
            return "";
        }
    }
}

