package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.gradomski.apartments.command.PagePath.*;

public class AdminToApartmentListCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String APARTMENT_LIST = "apartmentList";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        try{
            List<Apartment> allApartments = ApartmentServiceImpl.getInstance().getAllApartments();
            request.setAttribute(APARTMENT_LIST, allApartments);
            page = ADMIN_APARTMENTS;
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
    }
}
