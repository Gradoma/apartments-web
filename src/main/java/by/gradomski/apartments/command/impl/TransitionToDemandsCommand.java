package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.entity.DemandStatus;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.DemandServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;
import static by.gradomski.apartments.command.PagePath.DEMANDS;

public class TransitionToDemandsCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String DEMAND_LIST = "demandList";
    private static final String APARTMENT_ID = "apartmentId";
    private static final String CONTAINS_APPROVED = "containsApproved";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        String apartmentIdString = request.getParameter(APARTMENT_ID);
        if(apartmentIdString != null) {
            long apartmentId = Long.parseLong(apartmentIdString);
            try {
                List<Demand> demandList = DemandServiceImpl.getInstance().getActiveDemandsByApartmentId(apartmentId);
                Advertisement advertisement = AdServiceImpl.getInstance().getAdByApartmentId(apartmentId);
                List<Demand> filteredList = filterOldDemands(demandList, advertisement);
                if(!filteredList.isEmpty()) {
                    if(containsApproved(filteredList)){
                        request.setAttribute(CONTAINS_APPROVED, true);
                    }
                    request.setAttribute(DEMAND_LIST, filteredList);
                } else {
                    request.setAttribute(DEMAND_LIST, null);
                }
                page = DEMANDS;
            }catch (ServiceException e){
                log.error(e);
                page = ERROR_PAGE;
            }
        } else {
            log.error("apartmentIdString from request == null");
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
    }

    private boolean containsApproved(List<Demand> demandList){
        boolean result = false;
        Optional<Demand> resultOptional = demandList.stream()
                .filter(demand -> demand.getStatus()== DemandStatus.APPROVED)
                .findFirst();
        if(resultOptional.isPresent()){
            result = true;
        }
        return result;
    }

    private List<Demand> filterOldDemands(List<Demand> demandList, Advertisement advertisement){
        LocalDateTime advertisementCreation = advertisement.getCreationDate();
        return demandList.stream()
                .filter(demand -> demand.getCreationDate()
                        .isAfter(advertisementCreation))
                .collect(Collectors.toList());
    }
}
