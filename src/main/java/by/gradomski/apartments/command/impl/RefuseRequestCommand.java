package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Request;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.RequestServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRedirect();
        String page;
        long requestId = Long.parseLong(request.getParameter(REQUEST_ID));
        long apartmentId = Long.parseLong(request.getParameter(APARTMENT_ID));
        try{
            boolean refusingResult = RequestServiceImpl.getInstance().refuseRequest(requestId);
            HttpSession session = request.getSession(false);
            if(!refusingResult){
                session.setAttribute("refuseErrorMessage", "Error, try again.");        //todo as tmp atr
            }
            List<Request> requestList = RequestServiceImpl.getInstance().getActiveRequestsByApartmentId(apartmentId);
            session.setAttribute(REQUEST_LIST, requestList);
            //todo as tmp atr
//            if(!requestList.isEmpty()) {
//                request.setAttribute(REQUEST_LIST, requestList);
//            } else {
//                request.setAttribute(REQUEST_LIST, null);
//            }
            page = REQUESTS;
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
//        return page;
    }
}
