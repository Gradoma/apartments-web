package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.entity.DemandStatus;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.DemandServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.gradomski.apartments.command.PagePath.*;

public class TransitionToEstateCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String APARTMENT_LIST = "apartmentList";
    private static final String DEMAND_MAP = "demandMap";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        HttpSession session = request.getSession(false);
        if(session == null){
            log.info("session timed out");
            page = SIGN_IN;
        } else {
            User user = (User) session.getAttribute(USER);
            long userId = user.getId();
            try {
                List<Apartment> apartmentList = ApartmentServiceImpl.getInstance().getApartmentsByOwner(userId);
                if(apartmentList.isEmpty()){
                    request.setAttribute("noAppartmentsMessage", true);
                } else {
                    Map<Long, Boolean> demandMap = new HashMap<>();
                    for(Apartment apartment : apartmentList){
                        long apartmentId = apartment.getId();
                        List<Demand> apartmentDemandList = DemandServiceImpl.getInstance()
                                .getActiveDemandsByApartmentId(apartmentId);
                        if(containsApproved(apartmentDemandList)){
                            demandMap.put(apartmentId, true);
                        }
                    }
                    request.setAttribute(DEMAND_MAP, demandMap);
                }
                request.setAttribute(APARTMENT_LIST, apartmentList);
                page = ESTATE;
            } catch (ServiceException e) {
                log.error(e);
                page = ERROR_PAGE;
            }
        }
        router.setPage(page);
        return router;
    }

    private boolean containsApproved(List<Demand> demandList){
        Optional<Demand> optionalRequest = demandList.stream()
                .filter(request -> request.getStatus()== DemandStatus.APPROVED)
                .findAny();
        return optionalRequest.isPresent();
    }
}
