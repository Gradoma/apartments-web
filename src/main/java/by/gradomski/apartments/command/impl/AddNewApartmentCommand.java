package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.command.CommandType;
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

public class AddNewApartmentCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
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

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("start execute method");
        String page;
        HttpSession session = request.getSession(false);
        if(session == null) {
            log.info("session timed out");
            page = SIGN_IN;
        } else {
            User currentUser = (User) session.getAttribute(USER);
            log.debug("owner: " + currentUser);
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
                Map<String, String> addingResult = apartmentService.addApartment(currentUser, region, city, address,
                        rooms, floor, square, year, furniture, description);
                if (!addingResult.containsValue(FALSE)) {
                    List<Apartment> updatedApartmentList = apartmentService.getApartmentsByOwner(currentUser.getId());
                    session.setAttribute("apartmentList", updatedApartmentList);
                    page = ESTATE;
                } else {
                    String failReason = defineFalseKey(addingResult);
                    switch (failReason){
                        case REGION:
                            log.debug("incorrect region: " + region);
                            request.setAttribute("regionErrorMessage","Required filed");
                            break;
                        case CITY:
                            log.debug("incorrect city: " + city);
                            request.setAttribute("cityErrorMessage","Required filed");
                            break;
                        case ADDRESS:
                            log.debug("incorrect address: " + address);
                            request.setAttribute("addressErrorMessage","Required filed");
                            break;
                        case ROOMS:
                            log.debug("incorrect rooms: " + rooms);
                            request.setAttribute("roomsErrorMessage","Required filed, more 0");
                            break;
                        case FLOOR:
                            log.debug("incorrect floor: " + floor);
                            request.setAttribute("floorErrorMessage","Should be more 0");
                            break;
                        case SQUARE:
                            log.debug("incorrect square: " + square);
                            request.setAttribute("squareErrorMessage","Incorrect format or less 0");
                            break;
                        case YEAR:
                            log.debug("incorrect year: " + year);
                            request.setAttribute("yearErrorMessage","Invalid year build");
                            break;
                    }
                    page = NEW_ESTATE;
                }
            } catch (ServiceException e) {
                log.error(e);
                page = ERROR_PAGE;
            }
        }
        log.debug("return page: " + page);
        return page;
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
