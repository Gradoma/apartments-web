package by.gradomski.apartments.command;

import by.gradomski.apartments.command.impl.SignInCommand;

public enum CommandType {
    SIGN_IN(new SignInCommand());

    private Command command;

    CommandType (Command command){this.command = command;}

    public Command getCommand(){ return command;}
}
