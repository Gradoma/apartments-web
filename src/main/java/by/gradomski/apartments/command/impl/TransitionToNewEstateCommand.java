package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;

import javax.servlet.http.HttpServletRequest;

import static by.gradomski.apartments.command.PagePath.*;

public class TransitionToNewEstateCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        if(request.getSession(false) != null){
            page = NEW_ESTATE;
        } else {
            page = SIGN_IN;
        }
        return page;
    }
}
