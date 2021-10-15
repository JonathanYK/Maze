
public abstract class AbstractMaze2dGenerator implements Maze2dGenerator {
    String retStr;

    // Measuring algorithm execution time, returning millis as String
    public String measureAlgorithmTime(int mazeSize) throws mazeSizeException {

            if (mazeSize < 3) {
                throw new mazeSizeException("maze size has to be bigger then 2");
            }

            long startMeasuringTime = System.currentTimeMillis();
            generate(mazeSize);
            long finishMeasuringTime = System.currentTimeMillis();

            long totalMilis = finishMeasuringTime - startMeasuringTime;
            long seconds = (totalMilis / 1000) % 60;

            retStr = "\nExecuting took: "+ seconds + "." + String.format("%04d", totalMilis) + " seconds.\n";
            return retStr;



    }
}
