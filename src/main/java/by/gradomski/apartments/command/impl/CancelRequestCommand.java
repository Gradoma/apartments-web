package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.command.PagePath;
import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.entity.Request;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.RequestServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;
import static by.gradomski.apartments.command.PagePath.MY_REQUESTS;

public class CancelRequestCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String REQUEST_ID = "requestId";
    private static final String REQUEST_LIST = "requestList";
    private static final String ADVERTISEMENT_MAP = "advertisementMap";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String requestIdString = request.getParameter(REQUEST_ID);
        if(requestIdString != null){
            long requestId = Long.parseLong(requestIdString);
            try{
                boolean cancelingResult = RequestServiceImpl.getInstance().cancelRequest(requestId);
                if(!cancelingResult){
                    log.error("can't change status in Dao");
                    request.setAttribute("errorMessage", "Can't cancel request, try again");
                }
                HttpSession session = request.getSession(false);
                User currentUser = (User) session.getAttribute(USER);
                long userId = currentUser.getId();
                List<Request> requestList = RequestServiceImpl.getInstance().getRequestsByApplicantId(userId);
                request.setAttribute(REQUEST_LIST, requestList);
                Map<Long, Ad> advertisementMap = new HashMap<>();
                for(Request req : requestList){
                    long apartmentId = req.getApartmentId();
                    Ad ad = AdServiceImpl.getInstance().getAdByApartmentId(apartmentId);
                    advertisementMap.put(req.getId(), ad);
                }
                request.setAttribute(ADVERTISEMENT_MAP, advertisementMap);
                page = MY_REQUESTS;
            } catch (ServiceException e){
                log.error(e);
                page = ERROR_PAGE;
            }
        } else {
            log.error("request parameter requestId==null");
            page = ERROR_PAGE;
        }
        return page;
    }
}
