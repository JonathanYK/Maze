package controller;

import view.Command;

import java.util.HashMap;

public interface userCommands {

    // hashmap that holds all the created commands:
    HashMap<String, Command> commands = new HashMap<>();

    void putCommand(String string, Command command);
    void clearCommands();
    String getAllCommandNames();
    Command getCommand(String commandName);
    boolean isValidCommand(String providedCommand);


}

