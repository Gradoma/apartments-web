package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.util.PageCounter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;
import static by.gradomski.apartments.command.PagePath.SIGN_IN;

public class TransitionFromIndexCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String ADVERTISEMENT_LIST = "advertisementList";
    private static final String APARTMENT_MAP = "apartmentMap";
    private static final String PAGES_AMOUNT = "pagesAmount";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        try{
            List<Advertisement> advertisementList = AdServiceImpl.getInstance().getAllVisible();
            int pages = PageCounter.countPages(advertisementList);
            request.getServletContext().setAttribute(PAGES_AMOUNT, pages);
            Map<Long, Apartment> apartmentMap = new HashMap<>();
            for(Advertisement advetisement : advertisementList){
                long apartmentId = advetisement.getApartmentId();
                Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
                apartmentMap.put(advetisement.getId(), apartment);
            }
            request.getServletContext().setAttribute(APARTMENT_MAP, apartmentMap);
            request.getServletContext().setAttribute(ADVERTISEMENT_LIST, advertisementList);
            page = SIGN_IN;
        } catch (ServiceException e){
            log.error(e);
            e.printStackTrace();
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
    }
}
