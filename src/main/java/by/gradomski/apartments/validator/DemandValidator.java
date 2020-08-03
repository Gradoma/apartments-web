package by.gradomski.apartments.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class DemandValidator {
    private static final Logger log = LogManager.getLogger();
    private static final int DESCRIPTION_LENGTH = 150;
    private static final String DESCRIPTION = "description";
    private static final String EXPECTED_DATE = "expectedDate";
    private static final String FALSE = "false";

    public static Map<String, String> isValid(String dateString, String description) {
        Map<String, String> resultMap = new HashMap<>();
        if (dateString == null) {
            log.info("expected date is null");
            resultMap.put(EXPECTED_DATE, FALSE);
            return resultMap;
        }
        LocalDate today = LocalDate.now();
        try {
            LocalDate expectedDate = LocalDate.parse(dateString);
            if (expectedDate.isBefore(today)) {
                log.info("invalid expected date: earlier than today");
                resultMap.put(EXPECTED_DATE, FALSE);
                return resultMap;
            }
        } catch (DateTimeParseException e) {
            log.info(e);
            resultMap.put(EXPECTED_DATE, FALSE);
            return resultMap;
        }
        if (description != null && !description.isBlank()) {
            if (description.length() > DESCRIPTION_LENGTH) {
                log.info("description too long");
                resultMap.put(DESCRIPTION, FALSE);
                return resultMap;
            }
        }
        return resultMap;
    }
}
