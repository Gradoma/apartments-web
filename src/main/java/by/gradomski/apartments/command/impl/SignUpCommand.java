package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.gradomski.apartments.command.PagePath.*;

public class SignUpCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("start execute method");
        String page;
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String email = request.getParameter(EMAIL);
        try {
            if (userService.signUp(login, password, email)) {
                request.setAttribute("user", login);
                page = SIGN_IN;
            } else {
                // TODO request.setAttribute()
                log.info("incorrect login or password");
                page = SIGN_UP;
            }
        }catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        log.debug("return page: " + page);
        return page;
    }
}
