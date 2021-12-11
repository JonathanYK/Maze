package controller;

import view.Command;

import java.util.HashMap;


public abstract class userCommandsAbc implements userCommands {

    // hashmap that holds all the created commands:
    private HashMap<String, Command> commands;

    public userCommandsAbc() {
        this.commands = new HashMap<>();
    }

    public userCommandsAbc(HashMap<String, Command> commands) {
        this.commands = commands;
    }


    public void putCommand(String string, Command command) {
        commands.put(string, command);
    }

    public void clearCommands() {
        commands.clear();
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }

    public String getAllCommandNames() {
        StringBuilder retStrbld = new StringBuilder();
        for (String str: commands.keySet()) {
            retStrbld.append(str).append("\n");
        }
        return retStrbld.toString();
    }

    public boolean isValidCommand(String providedCommand) {
        return commands.containsKey(providedCommand);
    }
}
