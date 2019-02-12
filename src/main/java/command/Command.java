package command;

import command.exception.CommandException;

@FunctionalInterface
public interface Command {

    void execute() throws CommandException;
}