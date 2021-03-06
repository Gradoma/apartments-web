package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.DemandServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;
import static by.gradomski.apartments.command.PagePath.MY_RENT;

public class CancelDemandCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String DEMAND_ID = "demandId";
    private static final String DEMAND_LIST = "demandList";
    private static final String ADVERTISEMENT_MAP = "advertisementMap";
    private static final String APARTMENT_MAP = "apartmentMap";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRedirect();
        String page;
        String requestIdString = request.getParameter(DEMAND_ID);
        if(requestIdString != null){
            long requestId = Long.parseLong(requestIdString);
            try{
                HttpSession session = request.getSession(false);
                boolean cancelingResult = DemandServiceImpl.getInstance().cancelDemand(requestId);
                if(!cancelingResult){
                    log.error("can't change status in Dao");
                    session.setAttribute("errorMessage", "Can't cancel request, try again");
                }
                User currentUser = (User) session.getAttribute(USER);
                long userId = currentUser.getId();
                List<Demand> demandList = DemandServiceImpl.getInstance().getDemandsByApplicantId(userId);
                session.setAttribute(DEMAND_LIST, demandList);
                Map<Long, Advertisement> advertisementMap = new HashMap<>();
                Map<Long, Apartment> apartmentMap = new HashMap<>();
                for(Demand req : demandList){
                    long apartmentId = req.getApartmentId();
                    Advertisement advertisement = AdServiceImpl.getInstance().getAdByApartmentId(apartmentId);
                    advertisementMap.put(req.getId(), advertisement);
                    Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
                    apartmentMap.put(req.getId(), apartment);
                }
                session.setAttribute(ADVERTISEMENT_MAP, advertisementMap);
                session.setAttribute(APARTMENT_MAP, apartmentMap);
                page = MY_RENT;
            } catch (ServiceException e){
                log.error(e);
                page = ERROR_PAGE;
            }
        } else {
            log.error("request parameter requestId==null");
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
    }
}
