package model;

import java.io.IOException;

public interface ICommand {
    String validateParams(String param);
    String doCommand(String arg) throws ClassNotFoundException, IOException;
}
