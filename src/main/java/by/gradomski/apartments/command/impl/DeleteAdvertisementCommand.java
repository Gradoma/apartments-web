package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.entity.ApartmentStatus;
import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.entity.DemandStatus;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.DemandServiceImpl;
import by.gradomski.apartments.util.PageCounter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;
import static by.gradomski.apartments.command.PagePath.USER_PAGE;

public class DeleteAdvertisementCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String ADVERTISEMENT = "advertisement";
    private static final String ADVERTISEMENT_LIST = "advertisementList";
    private static final String PAGES_AMOUNT = "pagesAmount";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRedirect();
        String page;
        HttpSession session = request.getSession();
        Advertisement advertisement = (Advertisement) session.getAttribute(ADVERTISEMENT);
        try{
            long advertisementId = advertisement.getId();
            boolean advertisementStatusResult = AdServiceImpl.getInstance().changeVisibility(advertisementId);
            long apartmentId = advertisement.getApartmentId();
            List<Demand> apartmentDemandList = DemandServiceImpl.getInstance().getActiveDemandsByApartmentId(apartmentId);
            for(Demand apartmentReq : apartmentDemandList){
                if(apartmentReq.getStatus() != DemandStatus.APPROVED){
                    boolean updateStatusResult = DemandServiceImpl.getInstance().refuseDemand(apartmentReq.getId());
                    if(!updateStatusResult){
                        log.error("can't refuse apartment request: id=" + apartmentReq.getId());
                    }
                }
            }
            if(advertisementStatusResult){
                boolean apartmentStatusResult = ApartmentServiceImpl.getInstance()
                        .updateApartmentStatus(apartmentId, ApartmentStatus.REGISTERED);
                if(apartmentStatusResult){
                    request.getServletContext().removeAttribute(ADVERTISEMENT_LIST);
                    List<Advertisement> advertisementList = AdServiceImpl.getInstance().getAllVisible();
                    int pages = PageCounter.countPages(advertisementList);
                    request.getServletContext().setAttribute(PAGES_AMOUNT, pages);
                    request.getServletContext().setAttribute(ADVERTISEMENT_LIST, advertisementList);
                    session.removeAttribute(ADVERTISEMENT);
                    page = USER_PAGE;
                } else {
                    log.error("apartment status wasn't update");
                    page = ERROR_PAGE;
                }
            } else {
                log.error("ad status wasn't update");
                page = ERROR_PAGE;
            }
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
    }
}
