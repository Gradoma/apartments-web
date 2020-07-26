package by.gradomski.apartments.service;

import by.gradomski.apartments.exception.ServiceException;

import java.io.InputStream;
import java.util.Map;

public interface PhotoApartmentService {
    boolean add(InputStream inputStream, long apartmentId) throws ServiceException;
    Map<Long, String> getByApartmentId(long apartmentId) throws ServiceException;
    String getDefaultImage() throws ServiceException;
    boolean deleteAllApartmentPhotos(long apartmentId) throws ServiceException;
    boolean deletePhotoById(long id) throws ServiceException;
}
