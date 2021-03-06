package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.gradomski.apartments.command.PagePath.*;

public class EditApartmentCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String APARTMENT = "apartment";
    private static final String APARTMENT_LIST = "apartmentList";
    private static final String APARTMENT_ID = "apartmentId";
    private static final String REGION = "region";
    private static final String CITY = "city";
    private static final String ADDRESS = "address";
    private static final String ROOMS = "rooms";
    private static final String FLOOR = "floor";
    private static final String SQUARE = "square";
    private static final String YEAR = "year";
    private static final String FURNITURE = "furniture";
    private static final String DESCRIPTION = "description";
    private static final String FALSE = "false";
    private ApartmentServiceImpl apartmentService = ApartmentServiceImpl.getInstance();

    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRedirect();
        String page;
        HttpSession session = request.getSession(false);
        Apartment apartment = (Apartment) session.getAttribute(APARTMENT);
        long id = apartment.getId();
        User currentUser = (User) session.getAttribute(USER);
        String region = request.getParameter(REGION);
        String city = request.getParameter(CITY);
        String address = request.getParameter(ADDRESS);
        String rooms = request.getParameter(ROOMS);
        String floor = request.getParameter(FLOOR);
        String square = request.getParameter(SQUARE);
        String year = request.getParameter(YEAR);
        String furniture = request.getParameter(FURNITURE);
        String description = request.getParameter(DESCRIPTION);
        try {
            Map<String, String> updateResult = apartmentService.updateApartment(id, region, city, address, rooms,
                    floor, square, year,furniture, description);
            if (!updateResult.containsValue(FALSE)) {
                List<Apartment> updatedApartmentList = apartmentService.getApartmentsByOwner(currentUser.getId());
                session.setAttribute(APARTMENT_LIST, updatedApartmentList);
                session.removeAttribute(APARTMENT);
                page = ESTATE;
            } else {
                router.setForward();
                String failReason = defineFalseKey(updateResult);
                switch (failReason) {
                    case REGION:
                        log.debug("incorrect region: " + region);
                        request.setAttribute("regionError",true);
                        break;
                    case CITY:
                        log.debug("incorrect city: " + city);
                        request.setAttribute("cityError",true);
                        break;
                    case ADDRESS:
                        log.debug("incorrect address: " + address);
                        request.setAttribute("addressError",true);
                        break;
                    case ROOMS:
                        log.debug("incorrect rooms: " + rooms);
                        request.setAttribute("roomsError",true);
                        break;
                    case FLOOR:
                        log.debug("incorrect floor: " + floor);
                        request.setAttribute("floorError",true);
                        break;
                    case SQUARE:
                        log.debug("incorrect square: " + square);
                        request.setAttribute("squareError",true);
                        break;
                    case YEAR:
                        log.debug("incorrect year: " + year);
                        request.setAttribute("yearError",true);
                        break;
                    case DESCRIPTION:
                        request.setAttribute("descriptionError",true);
                        break;
                }
                page = EDIT_ESTATE;
            }
        } catch (ServiceException e) {
            log.error(e);
            page = ERROR_PAGE;
        }
        log.debug("return page: " + page);
        router.setPage(page);
        return  router;
    }

    private String defineFalseKey(Map<String, String> map){
        Optional<String> optionalResult = map.entrySet()
                .stream()
                .filter(entry -> FALSE.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();

        return optionalResult.get();
    }
}
