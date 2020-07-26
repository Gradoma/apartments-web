package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;

import javax.servlet.http.HttpServletRequest;

import static by.gradomski.apartments.command.PagePath.*;

public class TransitionToNewEstateCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        if(request.getSession(false) != null){
            page = NEW_ESTATE;
        } else {
            page = SIGN_IN;
        }
        router.setPage(page);
        return router;
    }
}
