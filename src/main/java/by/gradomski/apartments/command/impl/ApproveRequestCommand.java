package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.command.PagePath;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.ApartmentStatus;
import by.gradomski.apartments.entity.Request;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.RequestServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Provider;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.gradomski.apartments.command.PagePath.*;

public class ApproveRequestCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String REQUEST_ID = "requestId";
    private static final String APARTMENT_ID = "apartmentId";
    private static final String APARTMENT_LIST = "apartmentList";

    @Override
    public String execute(HttpServletRequest request) {     //TODO(make through transaction)
        //TODO(change ad visibility to 0)
        String page;
        long requestId = Long.parseLong(request.getParameter(REQUEST_ID));
        long apartmentId = Long.parseLong(request.getParameter(APARTMENT_ID));
        try{
            List<Request> requestList = RequestServiceImpl.getInstance().getRequestsByApartmentId(apartmentId);
            boolean approvingResult = RequestServiceImpl.getInstance().approveRequest(requestId, requestList);
            if(approvingResult){
                boolean apartmentResult = ApartmentServiceImpl.getInstance()
                        .updateApartmentStatus(apartmentId, ApartmentStatus.RENT);
                if(apartmentResult){
                    HttpSession session = request.getSession(false);
                    User user = (User) session.getAttribute(USER);
                    long userId = user.getId();
                    List<Apartment> apartmentList = ApartmentServiceImpl.getInstance().getApartmentsByOwner(userId);
                    request.setAttribute(APARTMENT_LIST, apartmentList);
                    page = ESTATE;
                } else {
                    log.debug("can't upd apartment status");
                    page = ERROR_PAGE;
                }
            } else {
                // message = request was cancled or deleted
                log.debug("request was cancled or deleted");
                page = REQUESTS;
            }
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        return page;
    }
}
