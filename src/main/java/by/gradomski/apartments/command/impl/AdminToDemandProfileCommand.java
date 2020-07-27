package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.Request;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.ApartmentService;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.RequestServiceImpl;
import by.gradomski.apartments.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.gradomski.apartments.command.PagePath.*;

public class AdminToDemandProfileCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String APARTMENT = "apartment";
    private static final String ID = "id";
    private static final String DEMAND = "demand";
    private static final String CURRENT_USER = "currentUser";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        String demandIdString = request.getParameter(ID);
        long demandId = Long.parseLong(demandIdString);
        try{
            Request demand = RequestServiceImpl.getInstance().getRequestById(demandId);
            request.setAttribute(DEMAND, demand);
            long apartmentId = demand.getApartmentId();
            Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
            request.setAttribute(APARTMENT, apartment);
            long applicantId = demand.getApplicant().getId();
            User applicant = UserServiceImpl.getInstance().getUserById(applicantId);
            request.setAttribute(CURRENT_USER, applicant);
            page = ADMIN_DEMAND_PROFILE;
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
    }
}
