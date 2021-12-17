package controller;

import view.ICommand;

import java.util.HashMap;

public interface IUserCommands {

    // Hashmap that holds all the created commands:
    HashMap<String, ICommand> _commands = new HashMap<>();

    void putCommand(String string, ICommand icommand);
    void clearCommands();
    String getAllCommandNames();
    ICommand getCommand(String commandName);
    boolean isValidCommand(String providedCommand);


}

