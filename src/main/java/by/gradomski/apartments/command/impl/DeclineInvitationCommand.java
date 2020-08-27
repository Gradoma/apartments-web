package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.DemandServiceImpl;
import by.gradomski.apartments.util.PageCounter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import static by.gradomski.apartments.command.PagePath.*;

public class DeclineInvitationCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String ADVERTISEMENT_ID = "advertisementId";
    private static final String DEMAND_ID = "demandId";
    private static final String ADVERTISEMENT_LIST = "advertisementList";
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
                    int pages = PageCounter.countPages(advertisementList);
                    request.getServletContext().setAttribute(PAGES_AMOUNT, pages);
                    Map<Long, Apartment> allApartmentsMap = (Map<Long, Apartment>) request
                            .getServletContext().getAttribute(APARTMENT_MAP);
                    Advertisement advertisement = AdServiceImpl.getInstance().getAdById(advertisementId);
                    long apartmentId = advertisement.getApartmentId();
                    Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
                    allApartmentsMap.put(advertisementId, apartment);
                    request.getServletContext().setAttribute(APARTMENT_MAP, allApartmentsMap);
                    page = USER_PAGE;
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
    }
}
