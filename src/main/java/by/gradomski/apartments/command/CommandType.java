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
    TRANSITION_TO_NEW_DEMAND(new TransitionToNewDemandCommand()),
    NEW_DEMAND(new NewDemandCommand()),
    TRANSITION_TO_DEMAND_LIST(new TransitionToDemandsCommand()),
    APPROVE_DEMAND(new ApproveDemandCommand()),
    REFUSE_DEMAND(new RefuseDemandCommand()),
    TRANSITION_TO_MY_RENT(new TransitionToMyRentCommand()),
    CANCEL_DEMAND(new CancelDemandCommand()),
    DECLINE_INVITATION(new DeclineInvitationCommand()),
    ACCEPT_INVITATION(new AcceptInvitationCommand()),
    FINISH_RENT(new FinishRentCommand()),
    TRANSITION_TO_ADVERTISEMENT_EDIT(new TransitionToAdvertisementEditCommand()),
    EDIT_ADVERTISEMENT(new EditAdvertisementCommand()),
    DELETE_ADVERTISEMENT(new DeleteAdvertisementCommand()),
    DELETE_PHOTO(new DeletePhotoCommand()),
    ADMIN_TO_USER_LIST(new AdminToUserListCommand()),
    ADMIN_TO_APARTMENT_LIST(new AdminToApartmentListCommand()),
    ADMIN_TO_DEMAND_LIST(new AdminToDemandListCommand()),
    ADMIN_TO_ADVERTISEMENT_LIST(new AdminToAdvertisementListCommand()),
    ADMIN_TO_USER_PROFILE(new AdminToUserProfileCommand()),
    ADMIN_TO_DEMAND_PROFILE(new AdminToDemandProfileCommand()),
    ADMIN_TO_ADVERTISEMENT_PROFILE(new AdminToAdvertisementProfileCommand()),
    ADMIN_TO_APARTMENT_PROFILE(new AdminToApartmentProfileCommand()),
    ADMIN_TO_NEW_ADMIN_FORM(new AdminToNewAdminForm()),
    REGISTER_NEW_ADMIN(new RegisterNewAdminCommand()),
    NEXT_ADVERTISEMENT(new NextAdvertisementPageCommand()),
    ADMIN_BAN_ADVERTISEMENT(new AdminBanAdvertisementCommand());

    private Command command;

    CommandType (Command command){this.command = command;}

    public Command getCommand(){ return command;}
}
