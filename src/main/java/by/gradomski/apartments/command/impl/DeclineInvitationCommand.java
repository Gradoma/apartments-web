package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.Request;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
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
import static by.gradomski.apartments.command.PagePath.MY_RENT;

public class DeclineInvitationCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String ADVERTISEMENT_ID = "advertisementId";
    private static final String REQUEST_ID = "requestId";
    private static final String ADVERTISEMENT_LIST = "advertisementList";
    private static final String USER = "user";
    private static final String REQUEST_LIST = "requestList";
    private static final String ADVERTISEMENT_MAP = "advertisementMap";
    private static final String APARTMENT_MAP = "apartmentMap";

    @Override
    public String execute(HttpServletRequest request) {     //TODO(make through transaction)
        String page;
        long requestId = Long.parseLong(request.getParameter(REQUEST_ID));
        long advertisementId = Long.parseLong(request.getParameter(ADVERTISEMENT_ID));
        try{
            boolean cancelResult = RequestServiceImpl.getInstance().cancelRequest(requestId);
            if(cancelResult){
                boolean advertisementStatusResult = AdServiceImpl.getInstance().changeVisibility(advertisementId);
                if(advertisementStatusResult){
                    List<Ad> adList= AdServiceImpl.getInstance().getAllVisible();
                    request.getServletContext().setAttribute(ADVERTISEMENT_LIST, adList);   //TODO(ASK: rewrite or should remove?)

                    HttpSession session = request.getSession(false);
                    User currentUser = (User) session.getAttribute(USER);
                    long userId = currentUser.getId();
                    List<Request> requestList = RequestServiceImpl.getInstance().getRequestsByApplicantId(userId);  //TODO(has same command - maybe filter?)
                    request.setAttribute(REQUEST_LIST, requestList);
                    Map<Long, Ad> advertisementMap = new HashMap<>();
                    Map<Long, Apartment> apartmentMap = new HashMap<>();
                    for(Request req : requestList){
                        long apartmentId = req.getApartmentId();
                        Ad ad = AdServiceImpl.getInstance().getAdByApartmentId(apartmentId);
                        advertisementMap.put(req.getId(), ad);
                        //copy-paste from transectionToMyRent
                        Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
                        apartmentMap.put(req.getId(), apartment);
                    }
                    request.setAttribute(APARTMENT_MAP, apartmentMap);
                    request.setAttribute(ADVERTISEMENT_MAP, advertisementMap);
                    page = MY_RENT;
                } else {
                    log.error("can't change ad status");
                    page = ERROR_PAGE;
                }
            } else {
                log.error("can't change request status");
                page = ERROR_PAGE;
            }
        }catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        return page;
    }
}
