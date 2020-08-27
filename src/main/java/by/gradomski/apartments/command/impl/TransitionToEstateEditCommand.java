package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.gradomski.apartments.command.PagePath.*;

public class TransitionToEstateEditCommand implements Command{
    private static final Logger log = LogManager.getLogger();
    private static final String APARTMENT_ID = "apartmentId";
    private static final String APARTMENT = "apartment";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        HttpSession session = request.getSession(false);
        if(session != null){              //TODO (filter)
            long apartmentId = Long.parseLong(request.getParameter(APARTMENT_ID));
            try {
                Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
                session.setAttribute(APARTMENT, apartment);
                page = EDIT_ESTATE;
            } catch (ServiceException e) {
                log.error(e);
                page = ERROR_PAGE;
            }
        } else {
            page = SIGN_IN;
        }
        router.setPage(page);
        return router;
    }
}
