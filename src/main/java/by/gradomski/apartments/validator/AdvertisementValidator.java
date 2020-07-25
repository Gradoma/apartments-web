package by.gradomski.apartments.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdvertisementValidator {
    private static final Logger log = LogManager.getLogger();
    private static final String PRICE_PATTERN = "^((\\p{Digit}){1,5}([.,]\\d{1,2})?)$";
    private static final int TITLE_LENGTH = 70;
    private static final String TITLE = "title";
    private static final String PRICE = "price";
    private static final String FALSE = "false";

    public static Map<String, String> isValid(String title, String price){
        Map<String, String> resultMap = new HashMap<>();
        if(title == null || title.isBlank() || title.strip().isBlank()){
            log.info("title blank or null");
            resultMap.put(TITLE, FALSE);
            return resultMap;
        }
        if(title.length() > TITLE_LENGTH){
            log.info("title too long");
            resultMap.put(TITLE, FALSE);
            return resultMap;
        }
        Pattern pattern = Pattern.compile(PRICE_PATTERN);
        Matcher matcher = pattern.matcher(price);
        if(!matcher.matches()){
            log.info("price doesnt match pattern: " + price);
            resultMap.put(PRICE, FALSE);
            return resultMap;
        }
        return resultMap;
    }
}
