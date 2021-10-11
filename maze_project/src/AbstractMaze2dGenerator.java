import java.util.concurrent.TimeUnit;

public abstract class AbstractMaze2dGenerator implements Maze2dGenerator {

    // Measuring algorithm execution time, returning millis as String
    public String measureAlgorithmTime() {
        try {

            long startMeasuringTime = System.currentTimeMillis();
            generate();
            long finishMeasuringTime = System.currentTimeMillis();

            return String.valueOf(finishMeasuringTime - startMeasuringTime);
        }

        catch(Exception ex) {
            ex.printStackTrace();
        }
        return "Failed to measuring algorithm time!";
    }
}
