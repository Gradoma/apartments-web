package by.gradomski.apartments.service.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final String EMAIL_PATTERN = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";
    private static final String LOGIN_PATTERN = "^[a-zA-Z0-9_-]{3,15}$";
    private static final String PASS_PATTERN = "^.{5,}$";
    private static Logger logger = LogManager.getLogger();

    public static boolean isValid(String login, String password){
        if(login == null || login.isBlank() || password == null || password.isEmpty()){
            logger.info("login or password null or empty");
            return false;
        }
        Pattern patternLogin = Pattern.compile(LOGIN_PATTERN);
        Matcher matcher = patternLogin.matcher(login);
        if( !matcher.matches()){
            logger.debug("login doesnt match pattern");
            return false;
        }
        Pattern patternPassword = Pattern.compile(PASS_PATTERN);
        matcher = patternPassword.matcher(password);
        if( !matcher.matches()){
            logger.debug("password doesnt match pattern");
            return false;
        }
        return true;
    }

    public static boolean isValid(String login, String password, String email){
        if(email == null || email.isEmpty()){
            logger.info("email null or empty");
            return false;
        }
        Pattern patternEmail = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = patternEmail.matcher(email);
        if( !matcher.matches()){
            logger.debug("email doesnt match pattern");
            return false;
        }
        return isValid(login, password);
    }
}
