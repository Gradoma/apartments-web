package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;

import javax.servlet.http.HttpServletRequest;

import static by.gradomski.apartments.command.PagePath.*;

public class TransitionToUserPage implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page = USER_PAGE;
        router.setPage(page);
        return router;
    }
}
