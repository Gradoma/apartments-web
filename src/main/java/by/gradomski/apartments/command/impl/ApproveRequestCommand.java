package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.*;
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
import java.util.Optional;

import static by.gradomski.apartments.command.PagePath.*;

public class ApproveRequestCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String REQUEST_ID = "requestId";
    private static final String APARTMENT_ID = "apartmentId";
    private static final String APARTMENT_LIST = "apartmentList";
    private static final String ADVERTISEMENT_LIST = "advertisementList";
    private static final String REQUEST_MAP = "requestMap";

    @Override
    public Router execute(HttpServletRequest request) {     //TODO(make through transaction)
        Router router = new Router();
        router.setRedirect();
        String page;
        long requestId = Long.parseLong(request.getParameter(REQUEST_ID));
        long apartmentId = Long.parseLong(request.getParameter(APARTMENT_ID));
        try{
            List<Request> requestList = RequestServiceImpl.getInstance().getActiveRequestsByApartmentId(apartmentId);
            boolean approvingResult = RequestServiceImpl.getInstance().approveRequestFromList(requestId, requestList);
            if(approvingResult){
                Ad advertisement = AdServiceImpl.getInstance().getAdByApartmentId(apartmentId);
                long advertisementId = advertisement.getId();
                boolean advertisementVisibilityChanging = AdServiceImpl.getInstance().changeVisibility(advertisementId);
                if(advertisementVisibilityChanging){
                    List<Ad> adList= AdServiceImpl.getInstance().getAllVisible();
                    request.getServletContext().setAttribute(ADVERTISEMENT_LIST, adList);
                    HttpSession session = request.getSession(false);
                    User user = (User) session.getAttribute(USER);
                    long userId = user.getId();
                    List<Apartment> apartmentList = ApartmentServiceImpl.getInstance().getApartmentsByOwner(userId);
                    session.setAttribute(APARTMENT_LIST, apartmentList);          //TODO(as tmp atr)
                    // copy-paste from transitionToEstateCommand
                    Map<Long, Boolean> requestMap = new HashMap<>();
                    for(Apartment apartment : apartmentList){
                        long id = apartment.getId();
                        List<Request> apartmentRequestList = RequestServiceImpl.getInstance()
                                .getActiveRequestsByApartmentId(id);
                        if(containsApproved(apartmentRequestList)){
                            requestMap.put(id, true);
                        }
                    }
                    session.setAttribute(REQUEST_MAP, requestMap);
                    page = ESTATE;
                } else {
                    log.debug("can't upd apartment status");
                    page = ERROR_PAGE;
                }
            } else {
                // message = request was cancled or deleted
                log.debug("request was cancled or deleted");
                page = REQUESTS;
            }
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
//        return page;
    }

    // copy-paste from transitionToEstateCommand
    private boolean containsApproved(List<Request> requestList){
        Optional<Request> optionalRequest = requestList.stream()
                .filter(request -> request.getStatus()== RequestStatus.APPROVED)
                .findAny();
        return optionalRequest.isPresent();
    }
}
