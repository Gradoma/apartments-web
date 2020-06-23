package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.mail.MailSender;
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
    private static final String EMAIL_SUBJECT = "Email address confirmation";
    private static final String EMAIL_TEXT = "<b>Thanks for registration!</b><br><p>Please confirm your email address - click the link below</p>";
    private static final String EMAIL_LINK = "<a href=http://localhost:8080/apartments_web_war/control?command=confirm_email&login=";
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
                String emailBody = emailBodyCreator(login);
                MailSender sender = new MailSender(email, EMAIL_SUBJECT, emailBody);
                sender.send();
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
