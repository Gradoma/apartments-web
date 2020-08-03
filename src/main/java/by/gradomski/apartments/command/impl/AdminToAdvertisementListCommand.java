package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.gradomski.apartments.command.PagePath.*;

public class AdminToAdvertisementListCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String ADVERTISEMENT_LIST = "advertisementList";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        try{
            List<Advertisement> allAdvertisements = AdServiceImpl.getInstance().getAll();
            request.setAttribute(ADVERTISEMENT_LIST, allAdvertisements);
            page = ADMIN_ADVERTISEMENTS;
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
    }
}
