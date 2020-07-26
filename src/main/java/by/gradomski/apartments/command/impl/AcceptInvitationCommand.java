package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.command.PagePath;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.*;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.ApartmentService;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.RequestServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;
import static by.gradomski.apartments.command.PagePath.USER_PAGE;

public class AcceptInvitationCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String APARTMENT_ID = "apartmentId";
    private static final String ADVERTISEMENT_ID = "advertisementId";
    private static final String REQUEST_ID = "requestId";
    private static final String ADVERTISEMENT_LIST = "advertisementList";

    @Override
    public Router execute(HttpServletRequest request) {     //TODO(make through transaction)
        Router router = new Router();
        router.setRedirect();
        long acceptingRequestId = Long.parseLong(request.getParameter(REQUEST_ID));
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute(USER);
        try{
            List<Request> userRequestList = RequestServiceImpl.getInstance().getRequestsByApplicantId(currentUser.getId());
            for(Request userReq : userRequestList){
                if(userReq.getId() == acceptingRequestId){
                    log.debug("accepting Req id=" + acceptingRequestId);
                    log.debug("accepting Req status=" + userReq.getStatus());
                    log.debug("accepting Req description=" + userReq.getDescription());
                    continue;
                }
                switch (userReq.getStatus()){
                    case APPROVED:
                        boolean cancelResult = RequestServiceImpl.getInstance().cancelRequest(userReq.getId());
                        if(cancelResult) {
                            long apartmentId = userReq.getApartmentId();
                            Ad ad = AdServiceImpl.getInstance().getAdByApartmentId(apartmentId);
                            boolean advertisementStatusResult = AdServiceImpl.getInstance().changeVisibility(ad.getId());
                            if (advertisementStatusResult) {
                                List<Ad> adList = AdServiceImpl.getInstance().getAllVisible();
                                request.getServletContext().setAttribute(ADVERTISEMENT_LIST, adList);
                            } else {
                                log.error("can't change ad status: advertisementId=" + ad.getId());
                            }
                        } else {
                            log.error("can't change request status: id=" + userReq.getId());
                        }
                        break;
                    case CREATED:
                        boolean cancelingResult = RequestServiceImpl.getInstance().cancelRequest(userReq.getId());
                        if(!cancelingResult){
                            log.error("can't cancel request: " + userReq.getId());
                        }
                        break;
                }
            }
            long apartmentId = Long.parseLong(request.getParameter(APARTMENT_ID));
            List<Request> apartmentRequestList = RequestServiceImpl.getInstance().getActiveRequestsByApartmentId(apartmentId);
            for(Request apartmentReq : apartmentRequestList){
                if(apartmentReq.getStatus() != RequestStatus.APPROVED){ ;
                    boolean updateStatusResult = RequestServiceImpl.getInstance().refuseRequest(apartmentReq.getId());
                    if(!updateStatusResult){
                        log.error("can't refuse apartment request: id=" + apartmentReq.getId());
                    }
                }
            }
            boolean apartmentTenantUpd = ApartmentServiceImpl.getInstance().updateTenant(apartmentId, currentUser.getId());
            if(!apartmentTenantUpd){
                log.error("can't upd apartment tenant: id=" + apartmentId);
            }
            boolean apartmentStatusUpd = ApartmentServiceImpl.getInstance()
                    .updateApartmentStatus(apartmentId, ApartmentStatus.RENT);
            if(!apartmentStatusUpd){
                log.error("can't upd apartment status: id=" + apartmentId);
            }
            router.setPage(USER_PAGE);
//            page = USER_PAGE;
        } catch (ServiceException e){
            log.error(e);
            router.setPage(ERROR_PAGE);
//            page = ERROR_PAGE;
        }
        return router;
//        return page;
    }
}
