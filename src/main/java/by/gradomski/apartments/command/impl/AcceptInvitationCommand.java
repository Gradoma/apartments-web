package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.*;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.DemandServiceImpl;
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
    private static final String DEMAND_ID = "demandId";
    private static final String ADVERTISEMENT_LIST = "advertisementList";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRedirect();
        long acceptingRequestId = Long.parseLong(request.getParameter(DEMAND_ID));
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute(USER);
        try{
            List<Demand> userDemandList = DemandServiceImpl.getInstance().getDemandsByApplicantId(currentUser.getId());
            for(Demand userReq : userDemandList){
                if(userReq.getId() == acceptingRequestId){
                    log.debug("accepting Req id=" + acceptingRequestId);
                    log.debug("accepting Req status=" + userReq.getStatus());
                    log.debug("accepting Req description=" + userReq.getDescription());
                    continue;
                }
                switch (userReq.getStatus()){
                    case APPROVED:
                        boolean cancelResult = DemandServiceImpl.getInstance().cancelDemand(userReq.getId());
                        if(cancelResult) {
                            long apartmentId = userReq.getApartmentId();
                            Advertisement advertisement = AdServiceImpl.getInstance().getAdByApartmentId(apartmentId);
                            boolean advertisementStatusResult = AdServiceImpl.getInstance().changeVisibility(advertisement.getId());
                            if (advertisementStatusResult) {
                                List<Advertisement> advertisementList = AdServiceImpl.getInstance().getAllVisible();
                                request.getServletContext().setAttribute(ADVERTISEMENT_LIST, advertisementList);
                            } else {
                                log.error("can't change advertisement status: advertisementId=" + advertisement.getId());
                            }
                        } else {
                            log.error("can't change request status: id=" + userReq.getId());
                        }
                        break;
                    case CREATED:
                        boolean cancelingResult = DemandServiceImpl.getInstance().cancelDemand(userReq.getId());
                        if(!cancelingResult){
                            log.error("can't cancel request: " + userReq.getId());
                        }
                        break;
                }
            }
            long apartmentId = Long.parseLong(request.getParameter(APARTMENT_ID));
            List<Demand> apartmentDemandList = DemandServiceImpl.getInstance().getActiveDemandsByApartmentId(apartmentId);
            for(Demand apartmentReq : apartmentDemandList){
                if(apartmentReq.getStatus() != DemandStatus.APPROVED){ ;
                    boolean updateStatusResult = DemandServiceImpl.getInstance().refuseDemand(apartmentReq.getId());
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
