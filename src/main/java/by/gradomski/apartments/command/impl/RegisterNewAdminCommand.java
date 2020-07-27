package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.mail.MailSender;
import by.gradomski.apartments.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

import static by.gradomski.apartments.command.PagePath.*;

public class RegisterNewAdminCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String LOGIN = "login";
    private static final String UNIQ_LOGIN = "loginUniq";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String FALSE = "false";
    private static final String EMAIL_SUBJECT = "Confirm your account!";
    private static final String EMAIL_TEXT_1 = "<b>Welcome</b><br><p>Your account parameters: </p><br>";
    private static final String EMAIL_TEXT_2 = "<p>To complete your registration - click the link below</p>";
    private static final String EMAIL_LINK = "<a href=http://localhost:8080/apartments_web_war/control?command=confirm_email&login=";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setRedirect();
        String page;
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String email = request.getParameter(EMAIL);
        try {
            Map<String, String> registrationResult = UserServiceImpl.getInstance()
                    .createNewAdmin(login, password, email);
            if (!registrationResult.containsValue(FALSE)) {
                page = ADMIN_USERS;
                String emailBody = emailBodyCreator(login, password, email);
                MailSender sender = new MailSender(email, EMAIL_SUBJECT, emailBody);
                sender.send();
            } else {
                router.setForward();
                String key = defineFalseKey(registrationResult);
                switch (key){
                    case LOGIN:
                        log.debug("incorrect login: " + login);
                        request.setAttribute("loginError",true);
                        break;
                    case PASSWORD:
                        log.debug("incorrect password: " + password);
                        request.setAttribute("passError",true);
                        break;
                    case EMAIL:
                        log.debug("incorrect email: " + email);
                        request.setAttribute("emailError",true);
                        break;
                    case UNIQ_LOGIN:
                        log.debug(login + " - user already exist");
                        request.setAttribute("uniqLoginError",true);
                        break;
                }
                page = ADMIN_NEW_ADMIN;
            }
        }catch (ServiceException e){
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

    private String emailBodyCreator(String loginParameter, String passwordParameter, String emailParameter){
        StringBuilder builder = new StringBuilder();
        builder.append(EMAIL_TEXT_1);
        builder.append(LOGIN);
        builder.append(": ");
        builder.append(loginParameter);
        builder.append("<br>");
        builder.append(PASSWORD);
        builder.append(": ");
        builder.append(passwordParameter);
        builder.append("<br>");
        builder.append(EMAIL_TEXT_2);
        builder.append("<br>");
        builder.append(EMAIL_LINK);
        builder.append(loginParameter);
        builder.append(">Confirm your email</a>");
        return builder.toString();
    }
}
