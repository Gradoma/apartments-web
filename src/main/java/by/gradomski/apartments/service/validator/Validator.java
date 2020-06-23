package by.gradomski.apartments.service.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final String LOGIN_PATTERN = "^[a-zA-Z0-9_-]{3,15}$";
    private static final String PASS_PATTERN = "^.{5,}$";
    private static final String EMAIL_PATTERN = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";
    private static final String FALSE = "false";
    private static Logger logger = LogManager.getLogger();

    public static boolean isValid(String login, String password){
        Map<String, String> fakeMap = isValid(login, password, new HashMap<String, String>());
        return !fakeMap.containsValue(FALSE);
    }

    private static Map<String, String> isValid(String login, String password, Map<String, String> resultMap){
        if(login == null || login.isBlank()){
            logger.info("login null or empty");
            resultMap.put("login", FALSE);
            return resultMap;
        }
        if(password == null || password.isEmpty()){
            logger.info("password null or empty");
            resultMap.put("password", FALSE);
            return resultMap;
        }
        Pattern patternLogin = Pattern.compile(LOGIN_PATTERN);
        Matcher matcher = patternLogin.matcher(login);
        if( !matcher.matches()){
            logger.debug("login doesnt match pattern");
            resultMap.put("login", FALSE);
            return resultMap;
        }
        Pattern patternPassword = Pattern.compile(PASS_PATTERN);
        matcher = patternPassword.matcher(password);
        if( !matcher.matches()){
            logger.debug("password doesnt match pattern");
            resultMap.put("password", FALSE);
            return resultMap;
        }
        return resultMap;
    }

    public static Map<String, String> isValid(String login, String password, String email, Map<String, String> resultMap){
        if(email == null || email.isEmpty()){
            logger.info("email null or empty");
            resultMap.put("email", FALSE);
            return resultMap;
        }
        Pattern patternEmail = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = patternEmail.matcher(email);
        if( !matcher.matches()){
            logger.debug("email doesnt match pattern");
            resultMap.put("email", FALSE);
            return resultMap;
        }
        return isValid(login, password, resultMap);
    }
}
