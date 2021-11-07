
public class creatingMazes {

    public static void main(String[] args) throws Exception {

        int mazeSize = 11;

        BFS bfsClass = new BFS();
        ASTAR astarClass = new ASTAR();


        // Generating simpleMaze:
        simpleMaze2DGenerator simpleMaze2DGenerator = new simpleMaze2DGenerator();
        maze2d simpleMaze2D = simpleMaze2DGenerator.generate(mazeSize);

        // Generating myMaze:
        myMaze2DGenerator myMaze2DGenerator = new myMaze2DGenerator();
        maze2d myMaze2D = myMaze2DGenerator.generate(mazeSize);


        // Measuring and printing simpleMaze
        System.out.println(simpleMaze2DGenerator.measureAlgorithmTime(mazeSize));
        System.out.println(simpleMaze2D.toString());


        // Solving simpleMaze using BFS:
        simpleMaze2D.solutionBFS = bfsClass.bfs(simpleMaze2D);
        System.out.println("\nsimpleMaze2D BFS:\n" + simpleMaze2D.solutionRoutePrinter(simpleMaze2D.solutionBFS));


        // Solving simpleMaze using A Star:
        simpleMaze2D.solutionAstar = astarClass.astar(simpleMaze2D);
        System.out.println("simpleMaze2D ASTAR:\n" + simpleMaze2D.solutionRoutePrinter(simpleMaze2D.solutionAstar));

        // Measuring and printing myMaze:
        System.out.println(myMaze2DGenerator.measureAlgorithmTime(mazeSize));
        System.out.println(myMaze2D.toString());

        // Solving myMaze using BFS:
        myMaze2D.solutionBFS = bfsClass.bfs(myMaze2D);
        System.out.println("\nmyMaze2D BFS:\n" + myMaze2D.solutionRoutePrinter(myMaze2D.solutionBFS));


        // Solving myMaze using A Star:
        myMaze2D.solutionAstar = astarClass.astar(myMaze2D);
        System.out.println("myMaze2D ASTAR:\n" + myMaze2D.solutionRoutePrinter(myMaze2D.solutionAstar));


        // simpleMaze printing of entrance,exit and available moves:
        System.out.println("\nsimpleMaze entrance position: " + simpleMaze2D.getEntrance().toString());
        System.out.println("\nsimpleMaze exit position: " + simpleMaze2D.getExit().toString());
        simpleMaze2D.getAvailableMoves(simpleMaze2D.entrance);


        // myMaze printing of entrance,exit and available moves:
        System.out.println("\nmyMaze maze entrance position: " + myMaze2D.getEntrance().toString());
        System.out.println("\nmyMaze maze exit position: " + myMaze2D.getExit().toString());
        myMaze2D.getAvailableMoves(myMaze2D.entrance);


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

