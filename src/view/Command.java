package view;

import java.io.IOException;

public interface Command {
    String doCommand(String arg) throws ClassNotFoundException, IOException;
}
