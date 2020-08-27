package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.DemandServiceImpl;
import by.gradomski.apartments.validator.DemandValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

import static by.gradomski.apartments.command.PagePath.*;

public class NewDemandCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER = "user";
    private static final String APARTMENT_ID = "apartmentId";
    private static final String EXPECTED_DATE = "expectedDate";
    private static final String DESCRIPTION = "description";
    private static final String FALSE = "false";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRedirect();
        String page;
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute(USER);
        String apartmentIdString = (String) session.getAttribute(APARTMENT_ID);
        String expectedDateString = request.getParameter(EXPECTED_DATE);
        String description = request.getParameter(DESCRIPTION);
        Map<String, String> validationResult = DemandValidator.isValid(expectedDateString, description);
        try{
            if(!validationResult.containsValue(FALSE)) {
                boolean result = DemandServiceImpl.getInstance().addDemand(currentUser, apartmentIdString,
                        expectedDateString, description);
                if (!result) {
                    router.setForward();
                    request.setAttribute("error", true);
                    page = NEW_DEMAND;
                } else {
                    session.removeAttribute(APARTMENT_ID);
                    page = USER_PAGE;
                }
            } else {
                router.setForward();
                String key = defineFalseKey(validationResult);
                switch (key){
                    case EXPECTED_DATE:
                        log.debug("incorrect expected date: " + expectedDateString);
                        request.setAttribute("dateError",true);
                        break;
                    case DESCRIPTION:
                        request.setAttribute("descriptionError",true);
                        break;
                }
                page = NEW_DEMAND;
            }
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
    }

    private String defineFalseKey(Map<String, String> map){
        Optional<String> optionalResult = map.entrySet()
                .stream()
                .filter(entry -> FALSE.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();

        return optionalResult.get();
    }
}
