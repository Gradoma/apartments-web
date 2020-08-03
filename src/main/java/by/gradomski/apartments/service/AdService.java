package by.gradomski.apartments.service;

import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;

import java.util.List;

public interface AdService {
    long addAdvertisement(String title, User author, String price, long apartmentId) throws ServiceException;
    List<Advertisement> getAll() throws ServiceException;
    Advertisement getAdByApartmentId(long id) throws ServiceException;
    Advertisement getAdById(long id) throws ServiceException;
    List<Advertisement> getAllVisible() throws ServiceException;
    boolean updateAd(Advertisement advertisement, String title, String price) throws ServiceException;
    boolean changeVisibility(long id) throws ServiceException;
    boolean deleteAd(long id) throws ServiceException;
}
