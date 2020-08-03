package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.DemandServiceImpl;
import by.gradomski.apartments.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static by.gradomski.apartments.command.PagePath.*;

public class AdminToAdvertisementProfileCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String APARTMENT = "apartment";
    private static final String ID = "id";
    private static final String ADVERTISEMENT = "advertisement";
    private static final String AUTHOR = "author";
    private static final String DEMAND_LIST = "demandList";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        String advertisementIdString = request.getParameter(ID);
        long advertisementId = Long.parseLong(advertisementIdString);
        try{
            Advertisement advertisement = AdServiceImpl.getInstance().getAdById(advertisementId);
            request.setAttribute(ADVERTISEMENT, advertisement);
            long apartmentId = advertisement.getApartmentId();
            Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
            request.setAttribute(APARTMENT, apartment);
            long authorId = advertisement.getAuthorId();
            User author = UserServiceImpl.getInstance().getUserById(authorId);
            request.setAttribute(AUTHOR, author);
            List<Demand> demandList = DemandServiceImpl.getInstance().getActiveDemandsByApartmentId(apartmentId);
            List<Demand> filteredList = filterOldDemands(demandList, advertisement);
            request.setAttribute(DEMAND_LIST, filteredList);
            page = ADMIN_ADVERTISEMENT_PROFILE;
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
    }

    private List<Demand> filterOldDemands(List<Demand> demandList, Advertisement advertisement){
        LocalDateTime advertisementCreation = advertisement.getCreationDate();
        return demandList.stream()
                .filter(demand -> demand.getCreationDate()
                        .isAfter(advertisementCreation))
                .collect(Collectors.toList());
    }
}
