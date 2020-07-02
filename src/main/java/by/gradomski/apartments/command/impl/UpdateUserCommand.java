package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.entity.Gender;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.text.ParseException;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;

import static by.gradomski.apartments.command.PagePath.*;

public class UpdateUserCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String GENDER = "gender";
    private static final String PHONE = "phone";
    private static final String BIRTHDAY = "birthday";
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        if(session == null){
            log.info("session timed out");
            page = SIGN_IN;
        } else {
            String login = request.getParameter(LOGIN);
            String password = request.getParameter(PASSWORD);
            Gender gender = Gender.valueOf(request.getParameter(GENDER));
            String firstName = request.getParameter(FIRST_NAME);
            log.debug("firstName: " + firstName);
            String lastName = request.getParameter(LAST_NAME);
            log.debug("lastName: " + lastName);
            String phone = request.getParameter(PHONE);
            String birthday = request.getParameter(BIRTHDAY);
            try {
                User afterUpdating = userService.updateUser(login, password, gender, firstName, lastName, phone, birthday);
                session.setAttribute("user", afterUpdating);
                page = USER_PAGE;
            } catch (ServiceException e) {
                if (e.getCause().getClass().equals(DateTimeParseException.class)) {
                    log.debug("caused by: " + e.getCause());
                    request.setAttribute("errorBirthday", "Invalid birthday");
                    page = USER_SETTINGS;
                } else {
                    log.error(e);
                    page = ERROR_PAGE;
                }
            }
        }
        log.debug("return page: " + page);
        return page;
    }
}
