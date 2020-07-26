package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;

import javax.servlet.http.HttpServletRequest;

import static by.gradomski.apartments.command.PagePath.SIGN_UP;

public class TransitionToSignUpCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setPage(SIGN_UP);
        return router;
    }
}
