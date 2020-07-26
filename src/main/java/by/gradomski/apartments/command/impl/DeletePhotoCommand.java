package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.PhotoApartmentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.gradomski.apartments.command.PagePath.EDIT_ESTATE;
import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;

public class DeletePhotoCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String PHOTO_ID = "photoId";
    private static final String APARTMENT_ID = "apartmentId";
    private static final String APARTMENT = "apartment";

    @Override
    public Router execute(HttpServletRequest request) {
        String page;
        Router router = new Router();
        router.setRedirect();
        long photoId = Long.parseLong(request.getParameter(PHOTO_ID));
        log.debug("photoId=" + photoId);
        try {
            boolean result = PhotoApartmentServiceImpl.getInstance().deletePhotoById(photoId);
            if(!result){
                log.warn("photo wasn't deleted: id=" + photoId);
            }
            long apartmentId = Long.parseLong(request.getParameter(APARTMENT_ID));
            log.info("apartmentId=" + apartmentId);
            Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
            HttpSession session = request.getSession(false);
            if(session != null){
                session.setAttribute(APARTMENT, apartment);
                page = EDIT_ESTATE;
            } else {
                log.error("session null");
                page = ERROR_PAGE;
            }
        } catch (ServiceException e) {
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
    }
}
