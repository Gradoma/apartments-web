package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.AdService;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.validator.AdvertisementValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.gradomski.apartments.command.PagePath.*;

public class EditAdvertisementCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String PRICE = "price";
    private static final String TITLE = "title";
    private static final String APARTMENT_ID = "apartmentId";
    private static final String REGION = "region";
    private static final String CITY = "city";
    private static final String ADDRESS = "address";
    private static final String ROOMS = "rooms";
    private static final String FLOOR = "floor";
    private static final String SQUARE = "square";
    private static final String YEAR = "year";
    private static final String FURNITURE = "furniture";
    private static final String DESCRIPTION = "description";
    private static final String ADVERTISEMENT = "advertisement";
    private static final String ADVERTISEMENT_LIST = "advertisementList";
    private static final String FALSE = "false";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        Ad advertisement = (Ad) session.getAttribute(ADVERTISEMENT);
        String title = request.getParameter(TITLE);
        String price = request.getParameter(PRICE);
        Map<String, String> validationResult = AdvertisementValidator.isValid(title, price);
        try{
            if(!validationResult.containsValue(FALSE)) {
                boolean result = AdServiceImpl.getInstance().updateAd(advertisement, title, price);
                if (!result) {
                    request.setAttribute("errorUpdate", true);
                    page = EDIT_ADVERTISEMENT;
                } else {
                    request.getServletContext().removeAttribute(ADVERTISEMENT_LIST);
                    List<Ad> adList = AdServiceImpl.getInstance().getAllVisible();
                    request.getServletContext().setAttribute(ADVERTISEMENT_LIST, adList);
                    session.removeAttribute(ADVERTISEMENT);
                    session.removeAttribute(APARTMENT_ID);
                    session.removeAttribute(REGION);
                    session.removeAttribute(CITY);
                    session.removeAttribute(ADDRESS);
                    session.removeAttribute(ROOMS);
                    session.removeAttribute(FLOOR);
                    session.removeAttribute(SQUARE);
                    session.removeAttribute(YEAR);
                    session.removeAttribute(FURNITURE);
                    session.removeAttribute(DESCRIPTION);
                    page = USER_PAGE;
                }
            } else {
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
                page = EDIT_ADVERTISEMENT;
            }
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        return page;
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
