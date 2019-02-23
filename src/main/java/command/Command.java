package command;

import command.exception.CommandException;

public interface Command {

    String execute() throws CommandException;
}