package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.gradomski.apartments.command.PagePath.*;

public class TransitionToNewDemandCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String APARTMENT_ID = "apartmentId";
    private static final String ADVERTISEMENT_ID = "advertisementId";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        HttpSession session = request.getSession();
        session.setAttribute(APARTMENT_ID, request.getParameter(APARTMENT_ID));
        User currentUser = (User) session.getAttribute(USER);
        if(currentUser == null){
            String idString = request.getParameter(ADVERTISEMENT_ID);
            if(idString != null) {
                log.info("user wasn't sign in");
                long advertisementId = Long.parseLong(idString);
                session.setAttribute(ADVERTISEMENT_ID, advertisementId);
                request.setAttribute("errorAccess", true);
                page = SIGN_IN;
            } else {
                log.error("request parameter " + ADVERTISEMENT_ID + "== null");
                page = ERROR_PAGE;
            }
        } else {
            page = NEW_DEMAND;
        }
        router.setPage(page);
        return router;
    }
}
