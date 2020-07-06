package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static by.gradomski.apartments.command.PagePath.*;

public class TransitionToEstateCommand implements Command {
    private static final Logger log = LogManager.getLogger();


    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        if(session == null){
            log.info("session timed out");
            page = SIGN_IN;
        } else {
            User user = (User) session.getAttribute("user");
            long userId = user.getId();
            try {
                List<Apartment> apartmentList = ApartmentServiceImpl.getInstance().getApartmentsByOwner(userId);
                request.setAttribute("apartmentList", apartmentList);
                page = ESTATE;
            } catch (ServiceException e) {
                log.error(e);
                page = ERROR_PAGE;
            }
        }
        return page;
    }
}
