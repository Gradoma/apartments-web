package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.RequestServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.format.DateTimeParseException;

import static by.gradomski.apartments.command.PagePath.*;

public class NewRequestCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String APARTMENT_ID = "apartmentId";
    private static final String EXPECTED_DATE = "expectedDate";
    private static final String DESCRIPTION = "description";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute(USER);
        String apartmentIdString = (String) session.getAttribute(APARTMENT_ID);
        String expectedDateString = request.getParameter(EXPECTED_DATE);
        String description = request.getParameter(DESCRIPTION);
        try{
            boolean result = RequestServiceImpl.getInstance().addRequest(currentUser, apartmentIdString,
                    expectedDateString, description);
            if(!result){
                request.setAttribute("error", "Request wasn't created, please try again.");
                page = NEW_REQUEST;
            } else {
                session.removeAttribute(APARTMENT_ID);
                page = USER_PAGE;
            }
        } catch (ServiceException e){
            if (e.getCause().getClass().equals(DateTimeParseException.class)) {
                log.debug("caused by: " + e.getCause());
                request.setAttribute("errorDate", "Invalid expected date");
                page = NEW_REQUEST;
            } else {
                log.error(e);
                page = ERROR_PAGE;
            }
        }
        return page;
    }
}
