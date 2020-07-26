package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.ApartmentStatus;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.RequestServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialException;

import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;
import static by.gradomski.apartments.command.PagePath.USER_PAGE;

public class FinishRentCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String APARTMENT_ID = "apartmentId";
    private static final String REQUEST_ID = "requestId";
    private static final long TENANT_ERASING_ID = -1;

    @Override
    public Router execute(HttpServletRequest request) {     //TODO(through transaction)
        Router router = new Router();
        router.setRedirect();
        String page;
        long apartmentId = Long.parseLong(request.getParameter(APARTMENT_ID));
        long requestId = Long.parseLong(request.getParameter(REQUEST_ID));
        try{
            boolean tenantErasingResult = ApartmentServiceImpl.getInstance()
                    .updateTenant(apartmentId, TENANT_ERASING_ID);
            if(tenantErasingResult){
               boolean statusUpdateResult = ApartmentServiceImpl.getInstance()
                       .updateApartmentStatus(apartmentId, ApartmentStatus.REGISTERED);
               if(statusUpdateResult){
                   boolean result = RequestServiceImpl.getInstance().archiveRequest(requestId);
                   if(!result){
                       log.error("can't archived request");
                   }
                   page = USER_PAGE;
               } else {
                   log.error("can't update apartment status");
                   page = ERROR_PAGE;
               }
            } else {
                log.error("can't set tenant null");
                page = ERROR_PAGE;
            }
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
    }
}
