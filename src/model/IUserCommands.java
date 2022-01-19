package model;

public interface IUserCommands {

    void putCommand(String string, ICommand icommand);
    void clearCommands();
    String getAllCommandNames();
    ICommand getCommand(String commandName) throws Exception;
}

