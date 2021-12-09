package controller;

import view.CLI;
import view.userCommands;

public class Demo {

    public static void main(String[] args) throws Exception {

        int mazeSize = 11;

        BFS bfsClass = new BFS();
        ASTAR astarClass = new ASTAR();

        solvedMaze2d simpleMazeSolved = new solvedMaze2d();
        solvedMaze2d myMazeSolved = new solvedMaze2d();

        mazeCompression MC = new mazeCompression();


        //-------------------------------------------------------------



        userCommands uc = new userCommands();

        //CLI cli = new CLI("is.txt", "os.txt");
        CLI cli = new CLI();

        cli.start(uc);


        //-------------------------------------------------------------

        // Generating simpleMaze:
        simpleMaze2DGenerator simpleMaze2DGenerator = new simpleMaze2DGenerator();
        maze2d simpleMaze2D = simpleMaze2DGenerator.generate(mazeSize);
        simpleMazeSolved.maze = simpleMaze2D;
        mazeSearchable simpleMS = new mazeSearchable(simpleMaze2D);



        // Decoding and Saving:
        String compressedFilename = MC.encodeHuffmanAndSave(simpleMaze2D);

        // Encoding and retrieving:
        maze2d encodedMaze2d = MC.decodeHuffmanMazeFileToMaze(compressedFilename);
        maze2d simpleDecoded = simpleMaze2DGenerator.generate(mazeSize);
        simpleDecoded.setCustomMaze(encodedMaze2d.mazeStructure);



        // Generating myMaze:
        myMaze2DGenerator myMaze2DGenerator = new myMaze2DGenerator();
        maze2d myMaze2D = myMaze2DGenerator.generate(mazeSize);
        myMazeSolved.maze = myMaze2D;
        mazeSearchable myMS = new mazeSearchable(myMaze2D);

        // Measuring and printing simpleMaze
        System.out.println(simpleMaze2DGenerator.measureAlgorithmTime(mazeSize));
        System.out.println(simpleMaze2D);

        // Measuring and printing myMaze:
        System.out.println(myMaze2DGenerator.measureAlgorithmTime(mazeSize));
        System.out.println(myMaze2D);


        // Solving simpleMaze searchable using controller.BFS:
        simpleMazeSolved.BFS = bfsClass.search(simpleMS);
        System.out.println("\nsimpleMaze2dSearchable controller.BFS:\n" + simpleMazeSolved.BFS.getSolution());

        // Solving myMaze searchable using controller.BFS:
        myMazeSolved.BFS = bfsClass.search(myMS);
        System.out.println("\nmyMaze2dSearchable controller.BFS:\n" + myMazeSolved.BFS.getSolution());


        // Solving simpleMaze using A Star:
        simpleMazeSolved.ASTAR = astarClass.search(simpleMS);
        System.out.println("\nsimpleMaze2dSearchable controller.ASTAR:\n" + simpleMazeSolved.ASTAR.getSolution());

        // Solving myMaze using A Star:
        myMazeSolved.ASTAR = astarClass.search(myMS);
        System.out.println("\nmyMaze2dSearchable controller.ASTAR:\n" +  myMazeSolved.ASTAR.getSolution());


        System.out.println("\ncontroller.BFS evaluation amount: " + bfsClass.getPointEvaluationAmount());
        System.out.println("controller.ASTAR evaluation amount: " + astarClass.getPointEvaluationAmount());

//        // simpleMaze printing of entrance,exit and available moves:
//        System.out.println("\nsimpleMaze entrance position: " + simpleMaze2D.getEntrance().toString());
//        System.out.println("\nsimpleMaze exit position: " + simpleMaze2D.getExit().toString());
//        simpleMaze2D.getAvailableMoves(simpleMaze2D.entrance);
//
//
//        // myMaze printing of entrance,exit and available moves:
//        System.out.println("\nmyMaze maze entrance position: " + myMaze2D.getEntrance().toString());
//        System.out.println("\nmyMaze maze exit position: " + myMaze2D.getExit().toString());
//        myMaze2D.getAvailableMoves(myMaze2D.entrance, myMaze2D.structure);


    }
}




//          In order to create custom maze:
//
//        boolean[][] customMaze = {
//                {true, true, true, true, false, false, false, true, true, false, false},
//                {false, true, true, true, false, false, false, true, true, false, true},
//                {true, true, true, true, true, false, false, true, false, true, true},
//                {false, true, true, false, false, false, false, true, true, true, true},
//                {true, true, true, true, false, false, true, true, true, true, true},
//                {false, true, false, true, true, true, true, false, true, true, true},
//                {true, false, false, true, true, false, true, true, false, false, true},
//                {true, true, false, false, true, true, true, true, true, false, true},
//                {true, false, false, true, true, true, true, true, true, true, true},
//                {true, true, false, true, true, true, true, true, true, false, true},
//                {true, true, false, true, true, true, true, true, false, false, true}
//        };
//
//        simpleMaze2D.setCustomMaze(customMaze);