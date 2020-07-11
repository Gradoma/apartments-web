package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static by.gradomski.apartments.command.PagePath.*;

public class DeleteApartmentCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String APARTMENT_ID = "apartmentId";
    private ApartmentServiceImpl apartmentService = ApartmentServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        if(session == null) {
            log.info("session timed out");
            page = SIGN_IN;
        } else {
            User currentUser = (User) session.getAttribute(USER);
            long id = Long.parseLong(request.getParameter(APARTMENT_ID));
            try{
                boolean result = ApartmentServiceImpl.getInstance().deleteApartment(id);
                if(result){
                    List<Apartment> updatedApartmentList = apartmentService.getApartmentsByOwner(currentUser.getId());
                    session.setAttribute("apartmentList", updatedApartmentList);
                } else {
                    request.setAttribute("errorDeleteMessage", "You can't delete this apartment");
                }
                page = ESTATE;
            } catch (ServiceException e){
                log.error(e);
                page = ERROR_PAGE;
            }
        }
        return page;
    }
}