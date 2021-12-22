package controller;

import model.ICommand;

import java.util.HashMap;

public abstract class IUserCommandsAbc implements IUserCommands {

    // hashmap that holds all the created commands:
    private HashMap<String, ICommand> commands;

    public IUserCommandsAbc() {
        this.commands = new HashMap<>();
    }

    public IUserCommandsAbc(HashMap<String, ICommand> commands) {
        this.commands = commands;
    }


    public void putCommand(String string, ICommand icommand) {
        commands.put(string, icommand);
    }

    public void clearCommands() {
        commands.clear();
    }

    public ICommand getCommand(String commandName) {
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
