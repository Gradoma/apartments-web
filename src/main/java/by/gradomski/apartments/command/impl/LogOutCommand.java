package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.gradomski.apartments.command.PagePath.SIGN_IN;

public class LogOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRedirect();
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        router.setPage(SIGN_IN);
        return router;
    }
}
