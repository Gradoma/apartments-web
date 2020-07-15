package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.gradomski.apartments.command.PagePath.ADVERTISEMENT;
import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;

public class TransitionToAdvertisementCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String ID = "id";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String idString = request.getParameter(ID);
        if(idString != null){
            long advertisementId = Long.parseLong(idString);
            log.debug("advertisement id: " + advertisementId);
            try{
                Ad ad = AdServiceImpl.getInstance().getAdById(advertisementId);
                request.setAttribute("advertisement", ad);
                long apartmentId = ad.getApartmentId();
                Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
                request.setAttribute("apartment", apartment);
                page = ADVERTISEMENT;
            } catch (ServiceException e){
                log.error(e);
                page = ERROR_PAGE;
            }
        } else {
            log.debug("apartmentId string == null");
        }
        return page;
    }
}
