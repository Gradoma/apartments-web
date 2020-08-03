package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.DemandServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;
import static by.gradomski.apartments.command.PagePath.DEMANDS;

public class RefuseDemandCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String DEMAND_ID = "demandId";
    private static final String DEMAND_LIST = "demandList";
    private static final String APARTMENT_ID = "apartmentId";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRedirect();
        String page;
        long requestId = Long.parseLong(request.getParameter(DEMAND_ID));
        long apartmentId = Long.parseLong(request.getParameter(APARTMENT_ID));
        try{
            boolean refusingResult = DemandServiceImpl.getInstance().refuseDemand(requestId);
            HttpSession session = request.getSession(false);
            if(!refusingResult){
                session.setAttribute("refuseErrorMessage", "Error, try again.");        //todo as tmp atr
            }
            List<Demand> demandList = DemandServiceImpl.getInstance().getActiveDemandsByApartmentId(apartmentId);
            session.setAttribute(DEMAND_LIST, demandList);
            //todo as tmp atr
//            if(!requestList.isEmpty()) {
//                request.setAttribute(REQUEST_LIST, requestList);
//            } else {
//                request.setAttribute(REQUEST_LIST, null);
//            }
            page = DEMANDS;
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
//        return page;
    }
}
