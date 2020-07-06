package by.gradomski.apartments.command;

import by.gradomski.apartments.command.impl.*;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    TRANSITION_TO_SIGN_UP(new TransitionToSignUpCommand()),
    CONFIRM_EMAIL(new ConfirmEmailCommand()),
    UPDATE_USER(new UpdateUserCommand()),
    TRANSITION_TO_SETTINGS(new TransitionToSettingsCommand()),
    TRANSITION_TO_ESTATE(new TransitionToEstateCommand()),
    TRANSITION_TO_NEW_ESTATE(new TransitionToNewEstateCommand()),
    ADD_NEW_APARTMENT(new AddNewApartmentCommand());

    private Command command;

    CommandType (Command command){this.command = command;}

    public Command getCommand(){ return command;}
}
