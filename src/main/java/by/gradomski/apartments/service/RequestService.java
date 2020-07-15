package by.gradomski.apartments.service;

import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;

public interface RequestService {
    boolean addRequest(User author, String apartmentIdString, String expectedDateString,
                       String description) throws ServiceException;
}
