package controller;

import model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MazeIUserCommands extends IUserCommandsAbc {

    private ArrayList<Maze2d> generatedMazes = new ArrayList<>();

    public void addGeneratedMazesPath(Maze2d genMaze) {
        this.generatedMazes.add(genMaze);
    }

    public Maze2d getGeneratedMaze(String desiredMazeName) {
        for (Maze2d maze : generatedMazes) {
            if (maze.getMazeName().equals(desiredMazeName))
                return maze;
        }
        return generatedMazes.get(0);
    }

    public void removeGeneratedMaze(String mazeName) {
        for (Maze2d maze : generatedMazes) {
            if (maze.getMazeName().equals(mazeName)) {
                generatedMazes.remove(maze);
                return;
            }
        }
    }

    public MazeIUserCommands() {
        super();
        this.putCommand("dir", new dirICommand());
        this.putCommand("genmaze", new generateICommand());
        this.putCommand("display", new mdisplayICommand());
        this.putCommand("savemaze", new saveICommand());
        this.putCommand("loadmaze", new loadICommand());
        this.putCommand("mazesize", new msizeICommand());
        this.putCommand("filesize", new fsizeICommand());
        this.putCommand("exit", new exitICommand());

    }


    // this method will print full path of the file in case file name provided as path, otherwise return all the files
    // in provided path:
    private class dirICommand implements ICommand {
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

    // generating mazes: kind-maze name-maze size
    // example: genmaze simplemaze-my_new_maze-15
    private class generateICommand implements ICommand {

        @Override
        public String doCommand(String genParams) {

            ArrayList<String> genParamsLst = new ArrayList<>(Arrays.asList(genParams.split("-")));
            IMaze2dGenerator imaze2DGenerator;

            // validate that the maze size is int:
            try {
                Integer.parseInt(genParamsLst.get(2));
            } catch (NumberFormatException nfe) {
                return "genmaze received not a number value, aborted!";
            }

            if (genParamsLst.get(0).equals("simplemaze")) {
                imaze2DGenerator = new SimpleIIMaze2DGenerator();
                // new SimpleIIMaze2DGenerator() should be in the model!
            }

             else if (genParamsLst.get(0).equals("mymaze")) {
                imaze2DGenerator = new MyIIMaze2DGenerator();
            }

            else {
                return "Wrong maze type!";
            }
                Maze2d genMaze2D = imaze2DGenerator.generate(Integer.parseInt(genParamsLst.get(2)));
                genMaze2D.setMazeName(genParamsLst.get(1));
                addGeneratedMazesPath(genMaze2D);
                return genParamsLst.get(1) + " " + genParamsLst.get(0) + " generated!";
        }
    }

        private class mdisplayICommand implements ICommand {
            @Override
            public String doCommand(String command) {

                StringBuilder retStr = new StringBuilder();
                ArrayList<String> commandLst = new ArrayList<>(Arrays.asList(command.split(" ")));

                // in case all mazes need to be shown (show all mazes names):
                boolean allMazes = commandLst.get(0).equals("mazes") || commandLst.get(0).equals("all");

                if (allMazes) {
                    for (Maze2d maze : generatedMazes) {
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
        private class saveICommand implements ICommand {

            @Override
            public String doCommand(String mazeName) throws IOException {

                MazeCompression MC = new MazeCompression();

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
    private class loadICommand implements ICommand {

        @Override
        public String doCommand(String path) throws IOException {

            MazeCompression MC = new MazeCompression();
            Maze2d encodedMaze2D = MC.decodeHuffmanMazeFileToMaze(path);
            generatedMazes.add(encodedMaze2D);

            return encodedMaze2D.getMazeName() + " loaded!";
        }
    }

    // get size of the maze before compression:
    private class msizeICommand implements ICommand {

        @Override
        public String doCommand(String mazeName) {
           Maze2d d = getGeneratedMaze(mazeName);

            return "maze size before compression: " + (d.getMazeStructure()[0].length * d.getMazeStructure().length);
        }
    }

    private class fsizeICommand implements ICommand {

        @Override
        public String doCommand(String path) throws IOException {

            long bytes = Files.size(Path.of(path));
            return "compressed size of file: " + bytes;
        }
    }

    private class exitICommand implements ICommand {

        @Override
        public String doCommand(String path)  {

            clearCommands();
            System.exit(0);
            return "";
        }
    }
}

