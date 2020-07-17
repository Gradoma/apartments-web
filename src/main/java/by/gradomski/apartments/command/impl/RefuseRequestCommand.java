package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.Request;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.RequestServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;
import static by.gradomski.apartments.command.PagePath.REQUESTS;

public class RefuseRequestCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String REQUEST_ID = "requestId";
    private static final String REQUEST_LIST = "requestList";
    private static final String APARTMENT_ID = "apartmentId";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        long requestId = Long.parseLong(request.getParameter(REQUEST_ID));
        long apartmentId = Long.parseLong(request.getParameter(APARTMENT_ID));
        try{
            boolean refusingResult = RequestServiceImpl.getInstance().refuseRequest(requestId);
            if(!refusingResult){
                request.setAttribute("refuseErrorMessage", "Error, try again.");
            }
            List<Request> requestList = RequestServiceImpl.getInstance().getRequestsByApartmentId(apartmentId);
            if(!requestList.isEmpty()) {
                request.setAttribute(REQUEST_LIST, requestList);
            } else {
                request.setAttribute(REQUEST_LIST, null);
            }
            page = REQUESTS;
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        return page;
    }
}
