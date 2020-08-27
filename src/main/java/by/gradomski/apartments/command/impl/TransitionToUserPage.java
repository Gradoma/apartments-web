package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;

import javax.servlet.http.HttpServletRequest;

import static by.gradomski.apartments.command.PagePath.*;

public class TransitionToUserPage implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        if(request.getSession(false) != null){              //TODO (filter)
            page = USER_PAGE;
        } else {
            page = SIGN_IN;
        }
        router.setPage(page);
        return router;
    }
}
