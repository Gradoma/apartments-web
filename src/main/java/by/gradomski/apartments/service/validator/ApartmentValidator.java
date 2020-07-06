package by.gradomski.apartments.service.validator;

import by.gradomski.apartments.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ApartmentValidator {
    private static final Logger log = LogManager.getLogger();
    private static final String FALSE = "false";
    private static final String REGION = "region";
    private static final String CITY = "city";
    private static final String ADDRESS = "address";
    private static final String ROOMS = "address";

    public static Map<String, String> isValid(String region, String city, String address, String rooms){
        Map<String, String> resultMap = new HashMap<>();
        if(region == null || region.isBlank() || region.strip().isBlank()){
            resultMap.put(REGION, FALSE);
            return resultMap;
        }
        if(city == null || city.isBlank() || city.strip().isBlank()){
            resultMap.put(CITY, FALSE);
            return resultMap;
        }
        if(address == null || address.isBlank() || address.strip().isBlank()){
            log.debug("address validation failed: " + address);
            resultMap.put(ADDRESS, FALSE);
            return resultMap;
        }
        if(rooms == null || rooms.isBlank() || rooms.strip().isBlank() || Integer.parseInt(rooms) <= 0){
            log.debug("rooms validation failed: " + rooms);
            resultMap.put(ROOMS, FALSE);
            return resultMap;
        }
        return resultMap;
    }
}
