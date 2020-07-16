package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;
import static by.gradomski.apartments.command.PagePath.SIGN_IN;

public class TransitionFromIndexCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String ADVERTISEMENT_LIST = "advertisementList";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        try{
            List<Ad> adList= AdServiceImpl.getInstance().getAllVisible();
            request.getServletContext().setAttribute(ADVERTISEMENT_LIST, adList);
            page = SIGN_IN;
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        return page;
    }
}
