package by.gradomski.apartments.service;

import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;

import java.util.List;

public interface AdService {
    long addAdvertisement(String title, User author, String price, long apartmentId) throws ServiceException;
    List<Ad> getAll() throws ServiceException;
    Ad getAdByApartmentId(long id) throws ServiceException;
    Ad getAdById(long id) throws ServiceException;
    List<Ad> getAllVisible() throws ServiceException;
    boolean updateAd(Ad advertisement, String title, String price) throws ServiceException;
    boolean changeVisibility(long id) throws ServiceException;
    boolean deleteAd(long id) throws ServiceException;
}
