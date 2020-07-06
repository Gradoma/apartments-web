package by.gradomski.apartments.service;

import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface ApartmentService {
    Map<String, String> addApartment(User owner, String region, String city, String address, String rooms, String floor,
                                     String square, String year, String furniture, String description) throws ServiceException;
    List<Apartment> getApartmentsByOwner(long id) throws ServiceException;
}
