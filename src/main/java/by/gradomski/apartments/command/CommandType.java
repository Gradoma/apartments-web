package by.gradomski.apartments.command;

import by.gradomski.apartments.command.impl.ConfirmEmailCommand;
import by.gradomski.apartments.command.impl.SignInCommand;
import by.gradomski.apartments.command.impl.SignUpCommand;
import by.gradomski.apartments.command.impl.TransitionToSignUpCommand;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    TRANSITION_TO_SIGN_UP(new TransitionToSignUpCommand()),
    CONFIRM_EMAIL(new ConfirmEmailCommand());

    private Command command;

    CommandType (Command command){this.command = command;}

    public Command getCommand(){ return command;}
}
