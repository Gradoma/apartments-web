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
            logger.error("login or pass null or empty");
            return false;
        }
        Pattern patternLogin = Pattern.compile(LOGIN_PATTERN);
        Matcher matcher = patternLogin.matcher(login);
        if( !matcher.matches()){
            logger.error("login doesnt match");
            return false;
        }
        Pattern patternPassword = Pattern.compile(PASS_PATTERN);
        matcher = patternPassword.matcher(password);
        if( !matcher.matches()){
            logger.error("pass doesnt match");
            return false;
        }
        return true;
    }

    public static boolean isValid(String login, String password, String email){
        if(email == null || email.isEmpty()){
            logger.error("email null or empty");
            return false;
        }
        Pattern patternEmail = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = patternEmail.matcher(email);
        if( !matcher.matches()){
            logger.error("email doesnt match");
            return false;
        }
        return isValid(login, password);
    }
}
