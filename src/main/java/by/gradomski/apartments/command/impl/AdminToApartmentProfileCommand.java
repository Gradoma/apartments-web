package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.Request;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static by.gradomski.apartments.command.PagePath.*;

public class AdminToApartmentProfileCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String APARTMENT = "apartment";
    private static final String ID = "id";
    private static final String ADVERTISEMENT = "advertisement";
    private static final String PHOTO_MAP = "photoMap";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        String apartmentIdString = request.getParameter(ID);
        long apartmentId = Long.parseLong(apartmentIdString);
        try{
            Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
            request.setAttribute(APARTMENT, apartment);
            Ad advertisement = AdServiceImpl.getInstance().getAdByApartmentId(apartmentId);
            request.setAttribute(ADVERTISEMENT, advertisement);
            Map<Long,String> photoMap = PhotoApartmentServiceImpl.getInstance().getByApartmentId(apartmentId);
            request.setAttribute(PHOTO_MAP, photoMap);
            page = ADMIN_APARTMENT_PROFILE;
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
    }
}
