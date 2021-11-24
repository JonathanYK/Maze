import java.awt.*;

public class Compressor {

    int entranceX;
    int entranceY;
    int exitX;
    int exitY;
    int size;
    boolean[][] structure;


    public Compressor(Point entrance, Point exit, int size, boolean[][] structure) {
        this.entranceX = entrance.x;
        this.entranceY = entrance.y;

        this.exitX = exit.x;
        this.exitY = exit.y;

        this.size = size;

        this.structure = structure;
    }

}
