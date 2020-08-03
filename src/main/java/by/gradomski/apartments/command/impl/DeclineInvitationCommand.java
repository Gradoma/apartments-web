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
import by.gradomski.apartments.util.PageCounter;
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
    private static final String DEMAND_ID = "demandId";
    private static final String ADVERTISEMENT_LIST = "advertisementList";
    private static final String USER = "user";
    private static final String DEMAND_LIST = "demandList";
    private static final String ADVERTISEMENT_MAP = "advertisementMap";
    private static final String APARTMENT_MAP = "apartmentMap";
    private static final String PAGES_AMOUNT = "pagesAmount";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRedirect();
        String page;
        long requestId = Long.parseLong(request.getParameter(DEMAND_ID));
        long advertisementId = Long.parseLong(request.getParameter(ADVERTISEMENT_ID));
        try{
            boolean cancelResult = DemandServiceImpl.getInstance().cancelDemand(requestId);
            if(cancelResult){
                boolean advertisementStatusResult = AdServiceImpl.getInstance().changeVisibility(advertisementId);
                if(advertisementStatusResult){
                    List<Advertisement> advertisementList = AdServiceImpl.getInstance().getAllVisible();
                    request.getServletContext().setAttribute(ADVERTISEMENT_LIST, advertisementList);

                    HttpSession session = request.getSession(false);
                    User currentUser = (User) session.getAttribute(USER);
                    long userId = currentUser.getId();
                    List<Demand> demandList = DemandServiceImpl.getInstance().getDemandsByApplicantId(userId);  //TODO(has same command - maybe filter?)
                    session.setAttribute(DEMAND_LIST, demandList);        //TODO(as tmp atr)
                    Map<Long, Advertisement> advertisementMap = new HashMap<>();
                    Map<Long, Apartment> apartmentMap = new HashMap<>();
                    for(Demand req : demandList){
                        long apartmentId = req.getApartmentId();
                        Advertisement advertisement = AdServiceImpl.getInstance().getAdByApartmentId(apartmentId);
                        advertisementMap.put(req.getId(), advertisement);
                        //copy-paste from transectionToMyRent
                        Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
                        apartmentMap.put(req.getId(), apartment);
                    }
                    int pages = PageCounter.countPages(advertisementList);
                    request.getServletContext().setAttribute(PAGES_AMOUNT, pages);
                    session.setAttribute(APARTMENT_MAP, apartmentMap);      //TODO(as tmp atr)
                    session.setAttribute(ADVERTISEMENT_MAP, advertisementMap);      //TODO(as tmp atr)
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
        router.setPage(page);
        return  router;
//        return page;
    }
}
