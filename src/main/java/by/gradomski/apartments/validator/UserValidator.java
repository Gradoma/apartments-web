package by.gradomski.apartments.validator;

import by.gradomski.apartments.entity.Gender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String LOGIN_PATTERN = "^[\\p{Digit}\\p{Alpha}_-]{3,15}$";
    private static final String PASS_PATTERN = "^.{5,45}$";
    private static final String EMAIL_PATTERN = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";
    private static final String NAME_PATTERN = "^[а-яА-я-]{1,45}$";
    private static final String PHONE_PATTERN = "^[+]?[(]?[0-9]{5}[)]?[-\\s]?[0-9]{3}[-\\s]?[0-9]{2}[-\\s]?[0-9]{2}$";
    private static final int EMAIL_LENGTH = 40;
    private static final int PHONE_LENGTH = 18;
    private static final String FALSE = "false";
    private static Logger logger = LogManager.getLogger();

    public static boolean isValid(String login, String password){
        Map<String, String> fakeMap = isValid(login, password, new HashMap<String, String>());
        return !fakeMap.containsValue(FALSE);
    }

    public static Map<String, String> isValid(String login, String password, String email){
        Map<String, String> resultMap = new HashMap<>();
        if(email == null || email.isEmpty()){
            logger.info("email null or empty");
            resultMap.put("email", FALSE);
            return resultMap;
        }
        if(email.length() > EMAIL_LENGTH){
            logger.info("email too long");
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

    public static Map<String, String> checkUserParameters(String genderString, String firstName,
                                                          String lastName, String phone, String birthdayString ) {
        HashMap<String, String> resultMap = new HashMap<>();
        Pattern namePattern = Pattern.compile(NAME_PATTERN);
        Matcher matcherFirstName = namePattern.matcher(firstName);
        if( !matcherFirstName.matches()){
            logger.info("first Name incorrect: " + firstName);
            resultMap.put("firstName", FALSE);
            return resultMap;
        }
        Matcher matcherLastName = namePattern.matcher(lastName);
        if( !matcherLastName.matches()){
            logger.info("last Name incorrect: " + lastName);
            resultMap.put("last Name", FALSE);
            return resultMap;
        }
        if(genderString != null && !genderString.isBlank()){
            try {
                Gender gender = Gender.valueOf(genderString);
            } catch (IllegalArgumentException e){
                logger.info("gender incorrect: " + genderString);
                resultMap.put("gender", FALSE);
                return resultMap;
            }
        }

        if(phone != null && !phone.isBlank()){
            if(phone.length() > PHONE_LENGTH){
                logger.info("phone too long");
                resultMap.put("phone", FALSE);
                return resultMap;
            }
            Pattern phonePattern = Pattern.compile(PHONE_PATTERN);
            Matcher matcher = phonePattern.matcher(phone);
            if( !matcher.matches()){
                logger.debug("phone doesnt match pattern, phone:" + phone);
                resultMap.put("phone", FALSE);
                return resultMap;
            }
        }

        if(birthdayString != null && !birthdayString.isBlank()){
            LocalDate today = LocalDate.now();
            try {
                LocalDate birthday = LocalDate.parse(birthdayString);
                if (today.isBefore(birthday)) {
                    logger.info("incorrect birthDay: later than today");
                    resultMap.put("birthday", FALSE);
                    return resultMap;
                }
            } catch (DateTimeParseException pEx){
                logger.info("invalid birthDay: can't be parsed:" + birthdayString);
                resultMap.put("birthday", FALSE);
                return resultMap;
            }
        }
        return resultMap;
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
}
