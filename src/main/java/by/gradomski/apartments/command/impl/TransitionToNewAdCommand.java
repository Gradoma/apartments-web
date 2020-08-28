package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.gradomski.apartments.command.PagePath.*;

public class TransitionToNewAdCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String APARTMENT_ID = "apartmentId";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        HttpSession session = request.getSession(false);
        session.setAttribute(APARTMENT_ID, request.getParameter(APARTMENT_ID));
        page = NEW_AD;
        router.setPage(page);
        return router;
    }
}
