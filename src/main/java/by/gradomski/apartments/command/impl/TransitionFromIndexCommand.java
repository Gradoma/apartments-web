package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
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
    private static final double ON_PAGE = 5.0;

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        try{
            List<Ad> adList= AdServiceImpl.getInstance().getAllVisible();
            int size = adList.size();
            int pages = (int) Math.ceil(size/ON_PAGE);
            int[] arrayPages = new int[pages];
            request.getServletContext().setAttribute(PAGES_AMOUNT, arrayPages);
            Map<Long, Apartment> apartmentMap = new HashMap<>();
            for(Ad advetisement : adList){
                long apartmentId = advetisement.getApartmentId();
                Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
                apartmentMap.put(advetisement.getId(), apartment);
            }
            request.getServletContext().setAttribute(APARTMENT_MAP, apartmentMap);
            request.getServletContext().setAttribute(ADVERTISEMENT_LIST, adList);
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
