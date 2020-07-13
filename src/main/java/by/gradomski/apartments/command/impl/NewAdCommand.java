package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static by.gradomski.apartments.command.PagePath.*;

public class NewAdCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String APARTMENT_ID = "apartmentId";
    private static final String TITLE = "title";
    private static final String PRICE = "price";

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("start execute method");
        String page;
        HttpSession session = request.getSession(false);
        if(session == null) {
            log.info("session timed out");
            page = SIGN_IN;
        } else {
            User adAuthor = (User) session.getAttribute(USER);
            String title = request.getParameter(TITLE);
            String price = request.getParameter(PRICE);
            long apartmentId = Long.parseLong(request.getParameter(APARTMENT_ID));
            try {
                boolean result = AdServiceImpl.getInstance().addAdvertisement(title, adAuthor, price, apartmentId);
                if(!result){
                    request.setAttribute("newAdErrorMessage", "Title and price are required fields. Try again");
                    request.setAttribute(APARTMENT_ID, apartmentId);
                    page = NEW_AD;
                } else {
                    ApartmentServiceImpl apartmentService = ApartmentServiceImpl.getInstance();
                    List<Apartment> updatedApartmentList = apartmentService.getApartmentsByOwner(adAuthor.getId());
                    session.setAttribute("apartmentList", updatedApartmentList);
                    page = ESTATE;
                }
            } catch (ServiceException e) {
                log.error(e);
                page = ERROR_PAGE;
            }
        }
        return page;
    }
}
