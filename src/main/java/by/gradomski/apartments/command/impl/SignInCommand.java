package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.command.PagePath;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.Role;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.gradomski.apartments.command.PagePath.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignInCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String ADVERTISEMENT_ID = "advertisementId";
    private static final String USER = "user";
    private static final String ADVERTISEMENT = "advertisement";
    private static final String APARTMENT = "apartment";
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRedirect();
        log.debug("start execute method");
        String page;
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        try {
            if (userService.signIn(login, password)) {
                User user = userService.getUserByLogin(login);
                request.getSession().setAttribute(USER, user);
                if(user.getRole() == Role.ADMIN){
                    page = ADMIN_USERS;
                    router.setPage(page);
                    return router;
                }
                HttpSession session = request.getSession(false);
                if(user.getFirstName() != null & user.getLastName() != null){
                    if(session != null){
                        Long advertisementId = (Long) session.getAttribute(ADVERTISEMENT_ID);
                        if(advertisementId != null){
                            Advertisement advertisement = AdServiceImpl.getInstance().getAdById(advertisementId);
                            session.setAttribute(ADVERTISEMENT, advertisement);
                            long apartmentId = advertisement.getApartmentId();
                            Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
                            session.setAttribute(APARTMENT, apartment);
                            page = PagePath.ADVERTISEMENT;
                        } else {
                            page = USER_PAGE;
                        }
                    } else {
                        page = ERROR_PAGE;
                    }
                } else {
                    page = USER_SETTINGS;
                }
            } else {
                router.setForward();
                request.setAttribute("errorSignInPass", true);
                log.info("incorrect login or password");
                page = SIGN_IN;
            }
        }catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        log.debug("return page: " + page);
        router.setPage(page);
        return router;
    }
}
