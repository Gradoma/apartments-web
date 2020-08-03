package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.DemandServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.gradomski.apartments.command.PagePath.*;

public class AdminToDemandListCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String DEMAND_LIST = "demandList";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        try{
            List<Demand> allDemands = DemandServiceImpl.getInstance().getAll();
            request.setAttribute(DEMAND_LIST, allDemands);
            page = ADMIN_DEMANDS;
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
    }
}
