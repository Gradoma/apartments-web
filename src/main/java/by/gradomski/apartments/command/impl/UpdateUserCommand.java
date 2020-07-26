package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.UserServiceImpl;
import by.gradomski.apartments.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.Optional;

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
    private static final String FALSE = "false";
    private static final String USER = "user";
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRedirect();
        String page;
        HttpSession session = request.getSession(false);
        if(session == null){
            log.info("session timed out");
            page = SIGN_IN;
        } else {
            String login = request.getParameter(LOGIN);
            String genderString = request.getParameter(GENDER);
            String firstName = request.getParameter(FIRST_NAME);
            log.debug("firstName: " + firstName);
            String lastName = request.getParameter(LAST_NAME);
            log.debug("lastName: " + lastName);
            String phone = request.getParameter(PHONE);
            String birthdayString = request.getParameter(BIRTHDAY);
            Map<String, String> checkResult = UserValidator.checkUserParameters(genderString, firstName, lastName, phone,
                    birthdayString);
            if(!checkResult.containsValue(FALSE)){
                try {
                    User afterUpdating = userService.updateUser(login, genderString, firstName, lastName, phone,
                            birthdayString);
                    session.setAttribute(USER, afterUpdating);
                    page = USER_PAGE;
                } catch (ServiceException e) {
                    log.error(e);
                    page = ERROR_PAGE;
                }
            } else {
                router.setForward();
                String key = defineFalseKey(checkResult);
                switch (key){
                    case FIRST_NAME:
                        log.debug("incorrect first name: " + login);
                        request.setAttribute("firstNameError",true);
                        break;
                    case LAST_NAME:
                        log.debug("incorrect last name: " + lastName);
                        request.setAttribute("lastNameError",true);
                        break;
                    case GENDER:
                        log.debug("incorrect gender: " + genderString);
                        request.setAttribute("genderErrorMessage",true);
                        break;
                    case PHONE:
                        log.debug("incorrect phone: " + phone);
                        request.setAttribute("phoneErrorMessage",true);
                        break;
                    case BIRTHDAY:
                        log.debug("incorrect birthday: " + birthdayString);
                        request.setAttribute("birthdayErrorMessage",true);
                        break;
                }
                page = USER_SETTINGS;
            }
        }
        log.debug("return page: " + page);
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
