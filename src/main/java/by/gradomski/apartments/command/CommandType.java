package by.gradomski.apartments.command;

import by.gradomski.apartments.command.impl.SignInCommand;
import by.gradomski.apartments.command.impl.SignUpCommand;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand());

    private Command command;

    CommandType (Command command){this.command = command;}

    public Command getCommand(){ return command;}
}
