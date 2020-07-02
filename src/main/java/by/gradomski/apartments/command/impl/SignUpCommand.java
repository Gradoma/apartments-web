package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.mail.MailSender;
import by.gradomski.apartments.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Optional;

import static by.gradomski.apartments.command.PagePath.*;

public class SignUpCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String LOGIN = "login";
    private static final String UNIQ_LOGIN = "loginUniq";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String EMAIL_SUBJECT = "Email address confirmation";
    private static final String EMAIL_TEXT = "<b>Thanks for registration!</b><br><p>Please confirm your email address - click the link below</p>";
    private static final String EMAIL_LINK = "<a href=http://localhost:8080/apartments_web_war/control?command=confirm_email&login=";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("start execute method");
        String page;
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String email = request.getParameter(EMAIL);
        try {
            Map<String, String> registrationResult = userService.signUp(login, password, email);
            if (!registrationResult.containsValue(FALSE)) {
                page = SIGN_IN;
                String emailBody = emailBodyCreator(login);
                MailSender sender = new MailSender(email, EMAIL_SUBJECT, emailBody);
                sender.send();
            } else {
                String key = defineFalseKey(registrationResult);
                switch (key){
                    case LOGIN:
                        log.debug("incorrect login: " + login);
                        request.setAttribute("loginErrorMessage","Invalid login (3-15 characters required)");
                        break;
                    case PASSWORD:
                        log.debug("incorrect password: " + password);
                        request.setAttribute("passErrorMessage","Too short: should be more than 5 symbols");
                        break;
                    case EMAIL:
                        log.debug("incorrect email: " + email);
                        request.setAttribute("emailErrorMessage","Incorrect email, check again");
                        break;
                    case UNIQ_LOGIN:
                        log.debug(login + " - user already exist");
                        request.setAttribute("loginErrorMessage","User with this login already exist");
                        break;
                }
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

    private String defineFalseKey(Map<String, String> map){
        Optional<String> optionalResult = map.entrySet()
                .stream()
                .filter(entry -> FALSE.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();

        return optionalResult.get();
    }

    private String emailBodyCreator(String loginParameter){
        StringBuilder builder = new StringBuilder();
        builder.append(EMAIL_TEXT);
        builder.append("<br>");
        builder.append(EMAIL_LINK);
        builder.append(loginParameter);
        builder.append(">Confirm your email</a>");
        return builder.toString();
    }
}
