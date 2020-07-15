package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.entity.Apartment;
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
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("start execute method");
        String page;
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        try {
            if (userService.signIn(login, password)) {
                User user = userService.getUserByLogin(login);
                log.debug("user ID after sign in: " + user.getId());
                request.getSession().setAttribute("user", user);
                if(user.getFirstName() != null & user.getLastName() != null){
                    HttpSession session = request.getSession(false);
                    if(session != null){
                        Long advertisementId = (Long) session.getAttribute(ADVERTISEMENT_ID);
                        if(advertisementId != null){
                            Ad ad = AdServiceImpl.getInstance().getAdById(advertisementId);
                            request.setAttribute("advertisement", ad);
                            long apartmentId = ad.getApartmentId();
                            Apartment apartment = ApartmentServiceImpl.getInstance().getApartmentByIdWithOwner(apartmentId);
                            request.setAttribute("apartment", apartment);
                            page = ADVERTISEMENT;
                        } else {
                            page = USER_PAGE;
                        }
                    } else {
                        page = ERROR_PAGE;
                    }
                } else {
                    request.setAttribute("greeting", "Welcome! Please finish your registration to use our app easily and conveniently");
                    page = USER_SETTINGS;
                }
            } else {
                request.setAttribute("errorSignInPass", "Incorrect login or password");
                log.info("incorrect login or password");
                page = SIGN_IN;
            }
        }catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        log.debug("return page: " + page);
        return page;
    }
}
