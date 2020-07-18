package by.gradomski.apartments.service;

import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.ApartmentStatus;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface ApartmentService {
    Map<String, String> addApartment(User owner, String region, String city, String address, String rooms, String floor,
                                     String square, String year, String furniture, String description) throws ServiceException;
    List<Apartment> getApartmentsByOwner(long id) throws ServiceException;
    List<Apartment> getApartmentsByTenant(long id) throws ServiceException;
    Apartment getApartmentByIdWithOwner(long id) throws ServiceException;
    Map<String, String> updateApartment(long id, String region, String city, String address, String rooms, String floor,
                                        String square, String year, String furniture, String description) throws ServiceException;
    boolean updateApartmentStatus(long id, ApartmentStatus status) throws ServiceException;
    boolean updateTenant(long apartmentId, long tenantId) throws ServiceException;
    boolean deleteApartment(long id) throws ServiceException;
}
