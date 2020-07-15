package by.gradomski.apartments.service.impl;

import by.gradomski.apartments.dao.impl.RequestDaoImpl;
import by.gradomski.apartments.entity.Request;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.RequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RequestServiceImpl implements RequestService {
    private static final Logger log = LogManager.getLogger();
    private static RequestServiceImpl instance;

    private RequestServiceImpl(){}

    public static RequestServiceImpl getInstance(){
        if(instance == null){
            instance = new RequestServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean addRequest(User author, String apartmentIdString, String expectedDateString,
                              String description) throws ServiceException {
        boolean flag;
        Request newRequest;
        if(apartmentIdString == null || apartmentIdString.isBlank()){
            throw new ServiceException("apartmentIdString == null or empty");
        }
        long apartmentId = Long.parseLong(apartmentIdString);
        if(expectedDateString == null || expectedDateString.isBlank()){
            throw new ServiceException("expectedDateString == null or empty");
        }
        LocalDate today = LocalDate.now();
        try {
            LocalDate expectedDate = LocalDate.parse(expectedDateString);
            if (today.isAfter(expectedDate)) {
                log.debug("invalid expected date: earlier than today");
                throw new DateTimeParseException("invalid expected date", expectedDateString, 0);
            }
            newRequest = new Request(author, apartmentId, expectedDate);
            if(description != null){
                newRequest.setDescription(description);
            }
            flag = RequestDaoImpl.getInstance().add(newRequest);
        } catch (DateTimeParseException | DaoException pEx){
            throw new ServiceException(pEx);
        }
        return flag;
    }
}
