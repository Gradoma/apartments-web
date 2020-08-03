package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.*;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.DemandServiceImpl;
import by.gradomski.apartments.util.PageCounter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.gradomski.apartments.command.PagePath.*;

public class ApproveDemandCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String DEMAND_ID = "demandId";
    private static final String APARTMENT_ID = "apartmentId";
    private static final String APARTMENT_LIST = "apartmentList";
    private static final String ADVERTISEMENT_LIST = "advertisementList";
    private static final String DEMAND_MAP = "demandMap";
    private static final String PAGES_AMOUNT = "pagesAmount";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRedirect();
        String page;
        long requestId = Long.parseLong(request.getParameter(DEMAND_ID));
        long apartmentId = Long.parseLong(request.getParameter(APARTMENT_ID));
        try{
            List<Demand> demandList = DemandServiceImpl.getInstance().getActiveDemandsByApartmentId(apartmentId);
            boolean approvingResult = DemandServiceImpl.getInstance().approveDemandFromList(requestId, demandList);
            if(approvingResult){
                Advertisement advertisement = AdServiceImpl.getInstance().getAdByApartmentId(apartmentId);
                long advertisementId = advertisement.getId();
                boolean advertisementVisibilityChanging = AdServiceImpl.getInstance().changeVisibility(advertisementId);
                if(advertisementVisibilityChanging){
                    List<Advertisement> advertisementList = AdServiceImpl.getInstance().getAllVisible();
                    request.getServletContext().setAttribute(ADVERTISEMENT_LIST, advertisementList);
                    HttpSession session = request.getSession(false);
                    User user = (User) session.getAttribute(USER);
                    long userId = user.getId();
                    List<Apartment> apartmentList = ApartmentServiceImpl.getInstance().getApartmentsByOwner(userId);
                    session.setAttribute(APARTMENT_LIST, apartmentList);
                    int pages = PageCounter.countPages(advertisementList);
                    request.getServletContext().setAttribute(PAGES_AMOUNT, pages);
                    Map<Long, Boolean> requestMap = new HashMap<>();
                    for(Apartment apartment : apartmentList){
                        long id = apartment.getId();
                        List<Demand> apartmentDemandList = DemandServiceImpl.getInstance()
                                .getActiveDemandsByApartmentId(id);
                        if(containsApproved(apartmentDemandList)){
                            requestMap.put(id, true);
                        }
                    }
                    session.setAttribute(DEMAND_MAP, requestMap);
                    page = ESTATE;
                } else {
                    log.debug("can't upd apartment status");
                    page = ERROR_PAGE;
                }
            } else {
                // message = request was cancled or deleted
                log.debug("request was cancled or deleted");
                page = DEMANDS;
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
    private boolean containsApproved(List<Demand> demandList){
        Optional<Demand> optionalRequest = demandList.stream()
                .filter(request -> request.getStatus()== DemandStatus.APPROVED)
                .findAny();
        return optionalRequest.isPresent();
    }
}
