package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.ApartmentStatus;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.util.AdvertisementComparator;
import by.gradomski.apartments.util.PageCounter;
import by.gradomski.apartments.validator.AdvertisementValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.gradomski.apartments.command.PagePath.*;

public class NewAdCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String APARTMENT_ID = "apartmentId";
    private static final String TITLE = "title";
    private static final String PRICE = "price";
    private static final String ADVERTISEMENT_LIST = "advertisementList";
    private static final String FALSE = "false";
    private static final String APARTMENT_MAP = "apartmentMap";
    private static final String PAGES_AMOUNT = "pagesAmount";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRedirect();
        String page;
        HttpSession session = request.getSession(false);
        if(session == null) {
            log.info("session timed out");
            page = SIGN_IN;
        } else {
            User adAuthor = (User) session.getAttribute(USER);
            String title = request.getParameter(TITLE);
            String price = request.getParameter(PRICE);
            long apartmentId = Long.parseLong((String) session.getAttribute(APARTMENT_ID));
            AdServiceImpl adService = AdServiceImpl.getInstance();
            ApartmentServiceImpl apartmentService = ApartmentServiceImpl.getInstance();
            Map<String, String> validationResult = AdvertisementValidator.isValid(title, price);
            try {
                if(!validationResult.containsValue(FALSE)){
                    long newAdvertisementId = adService.addAdvertisement(title, adAuthor, price, apartmentId);
                    if (newAdvertisementId > 0) {
                        boolean updateStatusResult = apartmentService.
                                updateApartmentStatus(apartmentId, ApartmentStatus.IN_DEMAND);
                        if (updateStatusResult) {
                            List<Apartment> updatedApartmentList = apartmentService.getApartmentsByOwner(adAuthor.getId());
                            session.removeAttribute(APARTMENT_ID);
                            session.setAttribute("apartmentList", updatedApartmentList); //TODO(change to request.attr?)

                            Advertisement newAdvertisement = AdServiceImpl.getInstance().getAdById(newAdvertisementId);
                            Object obj = request.getServletContext().getAttribute(ADVERTISEMENT_LIST);
                            log.debug("obj from servletContext ADVERTISEMENT_LIST: " + obj);    //FIXME(if this first ad -> obj == null?)
                            List<Advertisement> advertisementList = (List<Advertisement>) obj;
                            advertisementList.add(newAdvertisement);
                            advertisementList.sort(new AdvertisementComparator());

                            Map<Long, Apartment> apartmentMap = (Map<Long, Apartment>) request.getServletContext().getAttribute(APARTMENT_MAP);
                            Apartment apartment = apartmentService.getApartmentByIdWithOwner(apartmentId);
                            apartmentMap.put(newAdvertisement.getId(), apartment);

                            request.getServletContext().setAttribute(ADVERTISEMENT_LIST, advertisementList);
                            request.getServletContext().setAttribute(APARTMENT_MAP, apartmentMap);
                            int pages = PageCounter.countPages(advertisementList);
                            request.getServletContext().setAttribute(PAGES_AMOUNT, pages);
                            page = ESTATE;
                        } else {
                            router.setForward();
                            log.info("Advertisement was added; can't change apartment status");
                            boolean deleteResult = adService.deleteAd(newAdvertisementId);
                            if (!deleteResult) {
                                log.info("can't delete ad: id=" + newAdvertisementId);
                            }
                            request.setAttribute("newAdErrorMessage", "Some errors occurred. Try again");
                            page = NEW_AD;
                        }
                    } else {
                        router.setForward();
                        request.setAttribute("newAdErrorMessage", "Title and price are required fields. Try again");
                        page = NEW_AD;
                    }
                } else {
                    router.setForward();
                    String failReason = defineFalseKey(validationResult);
                    switch (failReason) {
                        case TITLE:
                            log.debug("incorrect title: " + title);
                            request.setAttribute("titleError", true);
                            break;
                        case PRICE:
                            log.debug("incorrect price: " + price);
                            request.setAttribute("priceError", true);
                            break;
                    }
                    page = NEW_AD;
                }
            } catch (ServiceException e) {
                log.error(e);
                page = ERROR_PAGE;
            }
        }
        router.setPage(page);
        return router;
    }

    private String defineFalseKey(Map<String, String> map){
        Optional<String> optionalResult = map.entrySet()
                .stream()
                .filter(entry -> FALSE.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();

        return optionalResult.get();
    }
}
