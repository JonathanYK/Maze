package model;

import java.io.IOException;

public interface ICommand {
    void doCommand(String arg) throws ClassNotFoundException, IOException;
    String validateParams(String param);
}
