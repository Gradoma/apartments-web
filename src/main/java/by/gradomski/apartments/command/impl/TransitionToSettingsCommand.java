package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.gradomski.apartments.command.PagePath.*;

public class TransitionToSettingsCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String LOGIN = "login";
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        if(request.getSession(false) != null){
            page = USER_SETTINGS;
        } else {
            page = SIGN_IN;
        }
//        try {
//            User user = userService.getUserByLogin(request.getParameter(LOGIN));
//            request.setAttribute("user", user);
//            page = USER_SETTINGS;
//        } catch (ServiceException e) {
//            log.error(e);
//            page = ERROR_PAGE;
//        }
        return page;
    }
}
