package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.DemandServiceImpl;
import by.gradomski.apartments.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.gradomski.apartments.command.PagePath.*;

public class AdminToUserProfileCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String APARTMENT_LIST = "apartmentList";
    private static final String ID = "id";
    private static final String DEMAND_LIST = "demandList";
    private static final String CURRENT_USER = "currentUser";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        String userIdString = request.getParameter(ID);
        long userId = Long.parseLong(userIdString);
        try{
            User currentUser = UserServiceImpl.getInstance().getUserById(userId);
            request.setAttribute(CURRENT_USER, currentUser);
            List<Apartment> usersApartmentList = ApartmentServiceImpl.getInstance().getApartmentsByOwner(userId);
            request.setAttribute(APARTMENT_LIST, usersApartmentList);
            List<Demand> usersDemandList = DemandServiceImpl.getInstance().getDemandsByApplicantId(userId);
            request.setAttribute(DEMAND_LIST, usersDemandList);
            page = ADMIN_USER_PROFILE;
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
    }
}
