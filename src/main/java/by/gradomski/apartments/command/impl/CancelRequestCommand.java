package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
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
import static by.gradomski.apartments.command.PagePath.MY_RENT;

public class CancelRequestCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String REQUEST_ID = "requestId";
    private static final String REQUEST_LIST = "requestList";
    private static final String ADVERTISEMENT_MAP = "advertisementMap";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRedirect();
        String page;
        String requestIdString = request.getParameter(REQUEST_ID);
        if(requestIdString != null){
            long requestId = Long.parseLong(requestIdString);
            try{
                HttpSession session = request.getSession(false);
                boolean cancelingResult = RequestServiceImpl.getInstance().cancelRequest(requestId);
                if(!cancelingResult){
                    log.error("can't change status in Dao");
                    session.setAttribute("errorMessage", "Can't cancel request, try again");
                }
                User currentUser = (User) session.getAttribute(USER);
                long userId = currentUser.getId();
                List<Request> requestList = RequestServiceImpl.getInstance().getRequestsByApplicantId(userId);
                session.setAttribute(REQUEST_LIST, requestList);        //TODO(as tmp atr)
                Map<Long, Ad> advertisementMap = new HashMap<>();
                for(Request req : requestList){
                    long apartmentId = req.getApartmentId();
                    Ad ad = AdServiceImpl.getInstance().getAdByApartmentId(apartmentId);
                    advertisementMap.put(req.getId(), ad);
                }
                session.setAttribute(ADVERTISEMENT_MAP, advertisementMap);      //TODO(as tmp atr)
                page = MY_RENT;
            } catch (ServiceException e){
                log.error(e);
                page = ERROR_PAGE;
            }
        } else {
            log.error("request parameter requestId==null");
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
//        return page;
    }
}
