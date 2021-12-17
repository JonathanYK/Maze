package controller;

import view.CLI;
import view.MazeIUserCommands;

public class Demo {

    public static void main(String[] args) throws Exception {

        BFS _bfsClass = new BFS();
        ASTAR _ASTARClass = new ASTAR();

        SolvedMaze2d _simpleMazeSolved = new SolvedMaze2d();
        SolvedMaze2d _myMazeSolved = new SolvedMaze2d();

        MazeCompression _MC = new MazeCompression();



        MazeIUserCommands _uc = new MazeIUserCommands();

        // IS is from/to a specific file:
//        CLI _cli = new CLI("is.txt", "os.txt");
//        _cli.start(_uc);

        // IS is from the terminal:
        CLI _cli = new CLI();
        _cli.start(_uc);


        int mazeSize = 11;

        // Generating simpleMaze:
        SimpleIIMaze2DGenerator _simpleMaze2DGenerator = new SimpleIIMaze2DGenerator();
        Maze2d simpleMaze2D = _simpleMaze2DGenerator.generate(mazeSize);
        _simpleMazeSolved._maze = simpleMaze2D;
        MazeISearchable _simpleMS = new MazeISearchable(simpleMaze2D);


        // Decoding and Saving:
        String compressedFilename = _MC.encodeHuffmanAndSave(simpleMaze2D);

        // Encoding and retrieving:
        Maze2d encodedMaze2D = _MC.decodeHuffmanMazeFileToMaze(compressedFilename);
        Maze2d simpleDecoded = _simpleMaze2DGenerator.generate(mazeSize);
        simpleDecoded.setCustomMaze(encodedMaze2D.mazeStructure);



        // Generating myMaze:
        MyIIMaze2DGenerator _myMaze2DGenerator = new MyIIMaze2DGenerator();
        Maze2d myMaze2D = _myMaze2DGenerator.generate(mazeSize);
        _myMazeSolved._maze = myMaze2D;
        MazeISearchable _myMS = new MazeISearchable(myMaze2D);

        // Measuring and printing simpleMaze
        System.out.println(_simpleMaze2DGenerator.measureAlgorithmTime(mazeSize));
        System.out.println(simpleMaze2D);

        // Measuring and printing myMaze:
        System.out.println(_myMaze2DGenerator.measureAlgorithmTime(mazeSize));
        System.out.println(myMaze2D);


        // Solving simpleMaze searchable using controller.BFS:
        _simpleMazeSolved._BFS = _bfsClass.search(_simpleMS);
        System.out.println("\nsimpleMaze2dSearchable controller.BFS:\n" + _simpleMazeSolved._BFS.getSolution());

        // Solving myMaze searchable using controller.BFS:
        _myMazeSolved._BFS = _bfsClass.search(_myMS);
        System.out.println("\nmyMaze2dSearchable controller.BFS:\n" + _myMazeSolved._BFS.getSolution());


        // Solving simpleMaze using A Star:
        _simpleMazeSolved._ASTAR = _ASTARClass.search(_simpleMS);
        System.out.println("\nsimpleMaze2dSearchable controller.ASTAR:\n" + _simpleMazeSolved._ASTAR.getSolution());

        // Solving myMaze using A Star:
        _myMazeSolved._ASTAR = _ASTARClass.search(_myMS);
        System.out.println("\nmyMaze2dSearchable controller.ASTAR:\n" +  _myMazeSolved._ASTAR.getSolution());


        System.out.println("\ncontroller.BFS evaluation amount: " + _bfsClass.getPointEvaluationAmount());
        System.out.println("controller.ASTAR evaluation amount: " + _ASTARClass.getPointEvaluationAmount());


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