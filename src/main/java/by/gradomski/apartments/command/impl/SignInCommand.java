package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.gradomski.apartments.command.PagePath.*;

import javax.servlet.http.HttpServletRequest;

public class SignInCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("start execute method");
        String page;
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        try {
            if (userService.signIn(login, password)) {
                request.setAttribute("user", login);
                page = USER_PAGE;
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
