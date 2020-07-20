package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.entity.ApartmentStatus;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
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

    @Override
    public String execute(HttpServletRequest request) {        // TODO(through transaction)
        String page;
        HttpSession session = request.getSession();
        Ad advertisement = (Ad) session.getAttribute(ADVERTISEMENT);
        try{
            boolean advertisementStatusResult = AdServiceImpl.getInstance().changeVisibility(advertisement.getId());
            if(advertisementStatusResult){
                long apartmentId = advertisement.getApartmentId();
                boolean apartmentStatusResult = ApartmentServiceImpl.getInstance()
                        .updateApartmentStatus(apartmentId, ApartmentStatus.REGISTERED);
                if(apartmentStatusResult){
                    request.getServletContext().removeAttribute(ADVERTISEMENT_LIST);
                    List<Ad> adList= AdServiceImpl.getInstance().getAllVisible();
                    request.getServletContext().setAttribute(ADVERTISEMENT_LIST, adList);
                    session.removeAttribute(ADVERTISEMENT);
                    page = USER_PAGE;       // TODO(or on Estate page?)
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
        return page;
    }
}
