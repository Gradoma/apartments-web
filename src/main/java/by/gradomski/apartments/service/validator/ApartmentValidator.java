package by.gradomski.apartments.service.validator;

import by.gradomski.apartments.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApartmentValidator {
    private static final Logger log = LogManager.getLogger();
    private static final String FALSE = "false";
    private static final String REGION = "region";
    private static final String CITY = "city";
    private static final String ADDRESS = "address";
    private static final String ROOMS = "rooms";
    private static final String FLOOR = "floor";
    private static final String SQUARE = "square";
    private static final String YEAR = "year";
    private static final String YEAR_PATTERN = "\\d{4}";

    public static Map<String, String> isValid(String region, String city, String address, String rooms, String floor,
                                              String square, String year){
        Map<String, String> resultMap = new HashMap<>();
        if(region == null || region.isBlank() || region.strip().isBlank()){
            log.info("region blank or null");
            resultMap.put(REGION, FALSE);
            return resultMap;
        }
        if(city == null || city.isBlank() || city.strip().isBlank()){
            log.info("city blank or null");
            resultMap.put(CITY, FALSE);
            return resultMap;
        }
        if(address == null || address.isBlank() || address.strip().isBlank()){
            log.info("address blank or null");
            resultMap.put(ADDRESS, FALSE);
            return resultMap;
        }
        if(rooms == null || rooms.isBlank() || rooms.strip().isBlank()){
            log.info("rooms blank or null");
            resultMap.put(ROOMS, FALSE);
            return resultMap;
        } else {
            try{
                int roomsInt = Integer.parseInt(rooms);
                if(roomsInt <= 0) {
                    log.info("rooms less 0: " + roomsInt);
                    resultMap.put(ROOMS, FALSE);
                    return resultMap;
                }
            } catch (NumberFormatException e){
                log.info("invalid rooms number: " + rooms);
                resultMap.put(ROOMS, FALSE);
                return resultMap;
            }
        }
        if(!floor.isBlank()){
            try{
                int floorInt = Integer.parseInt(floor);
                if(floorInt <= 0) {
                    log.info("floor less 0: " + floorInt);
                    resultMap.put(FLOOR, FALSE);
                    return resultMap;
                }
            } catch (NumberFormatException e){
                log.info("invalid floor number: " + floor);
                resultMap.put(FLOOR, FALSE);
                return resultMap;
            }
        }
        if(!square.isBlank()){
            try{
                double squareDouble = Double.parseDouble(square);
                if(Double.compare(squareDouble, 0.0) <= 0) {
                    log.info("square less 0: " + squareDouble);
                    resultMap.put(SQUARE, FALSE);
                    return resultMap;
                }
            } catch (NumberFormatException e){
                log.info("invalid square number: " + square);
                resultMap.put(SQUARE, FALSE);
                return resultMap;
            }
        }
        if(!year.isBlank()){
            Pattern patternYear = Pattern.compile(YEAR_PATTERN);
            Matcher matcher = patternYear.matcher(year);
            if(!matcher.matches()){
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
        return resultMap;
    }
}
