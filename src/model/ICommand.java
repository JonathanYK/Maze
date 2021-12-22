package model;

import java.io.IOException;

public interface ICommand {
    String doCommand(String arg) throws ClassNotFoundException, IOException;
}
