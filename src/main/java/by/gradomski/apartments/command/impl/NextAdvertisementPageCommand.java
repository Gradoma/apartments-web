package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.command.PagePath;
import by.gradomski.apartments.controller.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class NextAdvertisementPageCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String PAGE = "page";
    private static final double ON_PAGE = 5.0;
    private static final String FIRST_ADVERTISEMENT = "firstAdvertisement";
    private static final String LAST_ADVERTISEMENT = "lastAdvertisement";
    private static final String USER = "user";
    private static final String CURRENT_PAGE = "currentPage";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String pagePath;
        String pageString = request.getParameter(PAGE);
        int page = Integer.parseInt(pageString);
        if(page != 1) {
            int firstAdNumber = (int) ON_PAGE + 1;
            log.debug("first = " + firstAdNumber);
            int lastAdNumber = (int) (page * ON_PAGE);
            log.debug("last=" + lastAdNumber);
            request.setAttribute(FIRST_ADVERTISEMENT, firstAdNumber);
            request.setAttribute(LAST_ADVERTISEMENT, lastAdNumber);
            request.setAttribute(CURRENT_PAGE, page);
        }
        HttpSession session = request.getSession(false);
        if(session.getAttribute(USER) == null){
            pagePath = PagePath.SIGN_IN;
        } else {
            pagePath = PagePath.USER_PAGE;
        }
        router.setPage(pagePath);
        return router;
    }
}
