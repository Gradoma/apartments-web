package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.Apartment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.gradomski.apartments.command.PagePath.EDIT_ESTATE;
import static by.gradomski.apartments.command.PagePath.SIGN_IN;

public class TransitionToEstateEditCommand implements Command{
    private static final Logger log = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        if(session != null){
            session.setAttribute("apartmentId", request.getParameter("apartmentId"));
            session.setAttribute("region", request.getParameter("region"));
            session.setAttribute("city", request.getParameter("city"));
            session.setAttribute("address", request.getParameter("address"));
            session.setAttribute("rooms", request.getParameter("rooms"));
            session.setAttribute("floor", request.getParameter("floor"));
            session.setAttribute("square", request.getParameter("square"));
            session.setAttribute("year", request.getParameter("year"));
            session.setAttribute("furniture", request.getParameter("furniture"));
            session.setAttribute("description", request.getParameter("description"));
            page = EDIT_ESTATE;
        } else {
            page = SIGN_IN;
        }
        return page;
    }
}
