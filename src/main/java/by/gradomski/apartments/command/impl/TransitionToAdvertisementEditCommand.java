package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.entity.Request;
import by.gradomski.apartments.entity.RequestStatus;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.RequestServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static by.gradomski.apartments.command.PagePath.EDIT_ADVERTISEMENT;
import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;

public class TransitionToAdvertisementEditCommand implements Command {
    private static final Logger log = LogManager.getLogger();
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
    private static final String ADVERTISEMENT = "advertisement";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        long apartmentId = Long.parseLong(request.getParameter(APARTMENT_ID));
        try {
            Ad advertisement = AdServiceImpl.getInstance().getAdByApartmentId(apartmentId);
            HttpSession session = request.getSession();
            session.setAttribute(ADVERTISEMENT, advertisement);
            session.setAttribute(APARTMENT_ID, request.getParameter(APARTMENT_ID));
            session.setAttribute(REGION, request.getParameter(REGION));
            session.setAttribute(CITY, request.getParameter(CITY));
            session.setAttribute(ADDRESS, request.getParameter(ADDRESS));
            session.setAttribute(ROOMS, request.getParameter(ROOMS));
            session.setAttribute(FLOOR, request.getParameter(FLOOR));
            session.setAttribute(SQUARE, request.getParameter(SQUARE));
            session.setAttribute(YEAR, request.getParameter(YEAR));
            session.setAttribute(FURNITURE, request.getParameter(FURNITURE));
            session.setAttribute(DESCRIPTION, request.getParameter(DESCRIPTION));
            page = EDIT_ADVERTISEMENT;
        } catch (ServiceException e) {
            log.error(e);
            page = ERROR_PAGE;
        }
        return page;
    }
}
