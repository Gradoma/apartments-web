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
    ADD_NEW_APARTMENT(new AddNewApartmentCommand()),
    TRANSITION_TO_ESTATE_EDIT(new TransitionToEstateEditCommand()),
    EDIT_APARTMENT(new EditApartmentCommand()),
    DELETE_APARTMENT(new DeleteApartmentCommand()),
    TRANSITION_TO_USER_PAGE(new TransitionToUserPage()),
    LOG_OUT(new LogOutCommand()),
    TRANSITION_TO_NEW_AD(new TransitionToNewAdCommand()),
    NEW_AD(new NewAdCommand()),
    TRANSITION_FROM_INDEX(new TransitionFromIndexCommand()),
    TRANSITION_TO_ADVERTISEMENT(new TransitionToAdvertisementCommand()),
    TRANSITION_TO_NEW_REQUEST(new TransitionToNewRequestCommand()),
    NEW_REQUEST(new NewRequestCommand()),
    TRANSITION_TO_REQUEST_LIST(new TransitionToRequestsCommand()),
    APPROVE_REQUEST(new ApproveRequestCommand());

    private Command command;

    CommandType (Command command){this.command = command;}

    public Command getCommand(){ return command;}
}
