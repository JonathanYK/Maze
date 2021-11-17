public class Demo {

    public static void main(String[] args) throws Exception {

        int mazeSize = 11;

        BFS bfsClass = new BFS();
        ASTAR astarClass = new ASTAR();

        solvedMaze2d simpleMazeSolved = new solvedMaze2d();
        solvedMaze2d myMazeSolved = new solvedMaze2d();


        // Generating simpleMaze:
        simpleMaze2DGenerator simpleMaze2DGenerator = new simpleMaze2DGenerator();
        maze2d simpleMaze2D = simpleMaze2DGenerator.generate(mazeSize);
        simpleMazeSolved.maze = simpleMaze2D;
        mazeSearchable simpleMS = new mazeSearchable(simpleMaze2D);

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


        // Solving simpleMaze searchable using BFS:
        simpleMazeSolved.BFS = bfsClass.search(simpleMS);
        System.out.println("\nsimpleMaze2dSearchable BFS:\n" + simpleMazeSolved.BFS.getSolution());

        // Solving myMaze searchable using BFS:
        myMazeSolved.BFS = bfsClass.search(myMS);
        System.out.println("\nmyMaze2dSearchable BFS:\n" + myMazeSolved.BFS.getSolution());


        // Solving simpleMaze using A Star:
        simpleMazeSolved.ASTAR = astarClass.search(simpleMS);
        System.out.println("\nsimpleMaze2dSearchable ASTAR:\n" + simpleMazeSolved.ASTAR.getSolution());

        // Solving myMaze using A Star:
        myMazeSolved.ASTAR = astarClass.search(myMS);
        System.out.println("\nmyMaze2dSearchable ASTAR:\n" +  myMazeSolved.ASTAR.getSolution());


        System.out.println("\nBFS evaluation amount: " + bfsClass.getPointEvaluationAmount());
        System.out.println("ASTAR evaluation amount: " + astarClass.getPointEvaluationAmount());

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