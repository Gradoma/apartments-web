package by.gradomski.apartments.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApartmentValidator {
    private static final Logger log = LogManager.getLogger();
    private static final String REGION_CITY_PATTERN = "^[а-яА-я-.\\s]{1,45}$";
    private static final String ROOMS_FLOOR_PATTERN = "\\p{Digit}";
    private static final String SQUARE_PATTERN = "^(\\p{Digit}+([.,]\\d)?)$";
    private static final String YEAR_PATTERN = "\\p{Digit}{4}";
    private static final String FALSE = "false";
    private static final String REGION = "region";
    private static final String CITY = "city";
    private static final String ADDRESS = "address";
    private static final String ROOMS = "rooms";
    private static final String FLOOR = "floor";
    private static final String SQUARE = "square";
    private static final String YEAR = "year";
    private static final String DESCRIPTION = "description";
    private static final int ADDRESS_LENGTH = 45;
    private static final int DESCRIPTION_LENGTH = 200;
    private static Logger logger = LogManager.getLogger();

    public static Map<String, String> isValid(String region, String city, String address, String rooms, String floor,
                                              String square, String year, String description){
        Map<String, String> resultMap = new HashMap<>();
        if(region == null || region.isBlank() || region.strip().isBlank()){
            log.info("region blank or null");
            resultMap.put(REGION, FALSE);
            return resultMap;
        }
        Pattern regionCityPattern = Pattern.compile(REGION_CITY_PATTERN);
        Matcher regionMatcher = regionCityPattern.matcher(region);
        if( !regionMatcher.matches()){
            logger.debug("region doesnt match pattern: " + region);
            resultMap.put(REGION, FALSE);
            return resultMap;
        }
        if(city == null || city.isBlank() || city.strip().isBlank()){
            log.info("city blank or null");
            resultMap.put(CITY, FALSE);
            return resultMap;
        }
        Matcher cityMatcher = regionCityPattern.matcher(city);
        if( !cityMatcher.matches()){
            logger.debug("city doesnt match pattern: " + city);
            resultMap.put(CITY, FALSE);
            return resultMap;
        }
        if(address == null || address.isBlank() || address.strip().isBlank()){
            log.info("address blank or null");
            resultMap.put(ADDRESS, FALSE);
            return resultMap;
        }
        if(address.length() > ADDRESS_LENGTH){
            log.info("address to long");
            resultMap.put(ADDRESS, FALSE);
            return resultMap;
        }
        if(rooms == null || rooms.isBlank() || rooms.strip().isBlank()){
            log.info("rooms blank or null");
            resultMap.put(ROOMS, FALSE);
            return resultMap;
        }
        Pattern roomsFloorPattern = Pattern.compile(ROOMS_FLOOR_PATTERN);
        Matcher roomsMatcher = roomsFloorPattern.matcher(rooms);
        if(!roomsMatcher.matches()){
            log.info("invalid rooms number: " + rooms);
            resultMap.put(ROOMS, FALSE);
            return resultMap;
        }

        if(floor != null && !floor.isBlank()){
            Matcher floorMatcher = roomsFloorPattern.matcher(floor);
            if(!floorMatcher.matches()){
                log.info("invalid floor number: " + floor);
                resultMap.put(FLOOR, FALSE);
                return resultMap;
            }
        }
        if(square != null && !square.isBlank()){
            Pattern squarePattern = Pattern.compile(SQUARE_PATTERN);
            Matcher squareMatcher = squarePattern.matcher(square);
            if(!squareMatcher.matches()){
                log.info("invalid square number: " + square);
                resultMap.put(SQUARE, FALSE);
                return resultMap;
            }
        }

        if(year != null && !year.isBlank()){
            Pattern yearPattern = Pattern.compile(YEAR_PATTERN);
            Matcher yearMatcher = yearPattern.matcher(year);
            if(!yearMatcher.matches()){
                log.info("incorrect year format: " + year);
                resultMap.put(YEAR, FALSE);
                return resultMap;
            }
            int currentYear = LocalDate.now().getYear();
            if(Integer.parseInt(year) > currentYear){
                log.info("year after current year: " + year);
                resultMap.put(YEAR, FALSE);
                return resultMap;
            }
        }

        if(description != null && !description.isBlank()){
            if(description.length() > DESCRIPTION_LENGTH){
                log.info("description too long");
                resultMap.put(DESCRIPTION, FALSE);
                return resultMap;
            }
        }
        return resultMap;
    }
}
