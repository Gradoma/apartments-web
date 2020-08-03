package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.command.PagePath;
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

public class TransitionToAdvertisementCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String ID = "id";
    private static final String USER = "user";
    private static final String WAS_CREATED = "wasCreated";
    private static final String ADVERTISEMENT = "advertisement";
    private static final String APARTMENT = "apartment";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page = null;
        String idString = request.getParameter(ID);
        if(idString != null){
            long advertisementId = Long.parseLong(idString);
            log.debug("advertisement id: " + advertisementId);
            try{
                Advertisement advertisement = AdServiceImpl.getInstance().getAdById(advertisementId);
                request.setAttribute(ADVERTISEMENT, advertisement);
                long apartmentId = advertisement.getApartmentId();
                Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
                request.setAttribute(APARTMENT, apartment);
                HttpSession session = request.getSession(false);
                User currentUser = (User) session.getAttribute(USER);
                log.debug("advertisement.getAuthorId(): " + advertisement.getAuthorId());
                log.debug("user: " + currentUser);
                if(currentUser != null) {
                    List<Demand> userDemandList = DemandServiceImpl.getInstance()
                            .getDemandsByApplicantId(currentUser.getId());
                    for (Demand userReq : userDemandList) {
                        if (advertisement.getApartmentId() == userReq.getApartmentId() & userReq.getStatus() == DemandStatus.CREATED) {
                            log.debug("req id=" + userReq.getId());
                            log.debug("req status=" + userReq.getStatus());
                            request.setAttribute(WAS_CREATED, true);
                        }
                    }
                }
                page = PagePath.ADVERTISEMENT;
            } catch (ServiceException e){
                log.error(e);
                page = ERROR_PAGE;
            }
        } else {
            log.debug("apartmentId string == null");
        }
        router.setPage(page);
        return router;
    }
}
