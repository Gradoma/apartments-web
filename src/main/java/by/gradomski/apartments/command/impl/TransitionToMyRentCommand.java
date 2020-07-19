package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.Request;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.ApartmentService;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.RequestServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;
import static by.gradomski.apartments.command.PagePath.MY_REQUESTS;

public class TransitionToMyRentCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String REQUEST_LIST = "requestList";
    private static final String ADVERTISEMENT_MAP = "advertisementMap";
    private static final String APARTMENT_MAP = "apartmentMap";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute(USER);
        long userId = currentUser.getId();
        try{
            List<Request> requestList = RequestServiceImpl.getInstance().getRequestsByApplicantId(userId);
            request.setAttribute(REQUEST_LIST, requestList);
            Map<Long, Ad> advertisementMap = new HashMap<>();
            Map<Long, Apartment> apartmentMap = new HashMap<>();
            for(Request req : requestList){
                long apartmentId = req.getApartmentId();
                Ad ad = AdServiceImpl.getInstance().getAdByApartmentId(apartmentId);
                advertisementMap.put(req.getId(), ad);
                Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
                apartmentMap.put(req.getId(), apartment);
            }
            request.setAttribute(ADVERTISEMENT_MAP, advertisementMap);
            request.setAttribute(APARTMENT_MAP, apartmentMap);
            page = MY_REQUESTS;
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        return page;
    }
}
