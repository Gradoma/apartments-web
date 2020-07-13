package by.gradomski.apartments.service;

import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;

public interface AdService {
    long addAdvertisement(String title, User author, String price, long apartmentId) throws ServiceException;
    Ad getAdByApartmentId(long id) throws ServiceException;
    boolean updateAd(long id) throws ServiceException;
    boolean deleteAd(long id) throws ServiceException;
}
