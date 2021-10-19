public class creatingMazes {

    public static void main(String[] args) throws Exception {

        int mazeSize = 200;

        simpleMaze2DGenerator simpleMaze2DGenerator = new simpleMaze2DGenerator();
        System.out.println(simpleMaze2DGenerator.measureAlgorithmTime(mazeSize));
        // System.out.println(simpleMaze2DGenerator.simpleMaze.toString());


        myMaze2DGenerator myMaze2DGenerator = new myMaze2DGenerator();
        myMaze2DGenerator.measureAlgorithmTime(mazeSize);
        System.out.println(myMaze2DGenerator.measureAlgorithmTime(mazeSize));
        // System.out.println(myMaze2DGenerator.myMaze.toString());

    }
}
