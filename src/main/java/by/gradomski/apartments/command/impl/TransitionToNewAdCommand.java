package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.gradomski.apartments.command.PagePath.*;

public class TransitionToNewAdCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String APARTMENT_ID = "apartmentId";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        if(session != null){
            session.setAttribute(APARTMENT_ID, request.getParameter(APARTMENT_ID));//TODO(through session attribute) maybe?!
            page = NEW_AD;
        } else {
            page = SIGN_IN;
        }
        return page;
    }
}
