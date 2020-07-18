package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.*;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.RequestServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static by.gradomski.apartments.command.PagePath.ADVERTISEMENT;
import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;

public class TransitionToAdvertisementCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String ID = "id";
    private static final String USER = "user";
    private static final String WAS_CREATED = "wasCreated";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String idString = request.getParameter(ID);
        if(idString != null){
            long advertisementId = Long.parseLong(idString);
            log.debug("advertisement id: " + advertisementId);
            try{
                Ad ad = AdServiceImpl.getInstance().getAdById(advertisementId);
                request.setAttribute("advertisement", ad);
                long apartmentId = ad.getApartmentId();
                Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
                request.setAttribute("apartment", apartment);
                HttpSession session = request.getSession(false);
                User currentUser = (User) session.getAttribute(USER);
                List<Request> userRequestList = RequestServiceImpl.getInstance()
                        .getRequestsByApplicantId(currentUser.getId());
                for(Request userReq : userRequestList){
                    if(ad.getApartmentId() == userReq.getApartmentId() && userReq.getStatus()== RequestStatus.CREATED){
                        request.setAttribute(WAS_CREATED, true);
                    }
                }
                page = ADVERTISEMENT;
            } catch (ServiceException e){
                log.error(e);
                page = ERROR_PAGE;
            }
        } else {
            log.debug("apartmentId string == null");
        }
        return page;
    }
}
