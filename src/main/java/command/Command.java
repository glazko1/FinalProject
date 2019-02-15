package command;

import command.exception.CommandException;

public interface Command {

    void execute() throws CommandException;
}