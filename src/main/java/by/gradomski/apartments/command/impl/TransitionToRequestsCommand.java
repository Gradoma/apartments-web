package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.Request;
import by.gradomski.apartments.entity.RequestStatus;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.RequestServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;
import static by.gradomski.apartments.command.PagePath.REQUESTS;

public class TransitionToRequestsCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String REQUEST_LIST = "requestList";
    private static final String APARTMENT_ID = "apartmentId";
    private static final String CONTAINS_APPROVED = "containsApproved";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String apartmentIdString = request.getParameter(APARTMENT_ID);
        if(apartmentIdString != null) {
            long apartmentId = Long.parseLong(apartmentIdString);
            try {
                List<Request> requestList = RequestServiceImpl.getInstance().getActiveRequestsByApartmentId(apartmentId);
                if(!requestList.isEmpty()) {
                    if(containsApproved(requestList)){
                        request.setAttribute(CONTAINS_APPROVED, true);
                    }
                    request.setAttribute(REQUEST_LIST, requestList);
                } else {
                    request.setAttribute(REQUEST_LIST, null);
                }
                page = REQUESTS;
            }catch (ServiceException e){
                log.error(e);
                page = ERROR_PAGE;
            }
        } else {
            log.error("apartmentIdString from request == null");
            page = ERROR_PAGE;
        }
        return page;
    }

    private boolean containsApproved(List<Request> requestList){
        boolean result = false;
        Optional<Request> resultOptional = requestList.stream()
                .filter(request -> request.getStatus()== RequestStatus.APPROVED)
                .findFirst();
        if(resultOptional.isPresent()){
            result = true;
        }
        return result;
    }
}
