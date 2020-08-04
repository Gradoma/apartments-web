package by.gradomski.apartments.service;

import by.gradomski.apartments.exception.ServiceException;

import java.io.InputStream;
import java.util.Map;

/**
 *  Service interface for handle photos for Apartment entities{@link by.gradomski.apartments.entity.Apartment}
 */
public interface PhotoApartmentService {

    /**
     * Add photo to database with related advertisement
     * @param inputStream photo represented by input stream
     * @param apartmentId apartment id
     * @return true if photo added to database
     * @throws ServiceException if DaoException thrown
     */
    boolean add(InputStream inputStream, long apartmentId) throws ServiceException;

    /**
     * Get photos related with this apartment id
     * @param apartmentId apartment id
     * @return Map<Long, String> where long is photoId from database, String is photo converted to base64 string
     * @throws ServiceException if DaoException thrown
     */
    Map<Long, String> getByApartmentId(long apartmentId) throws ServiceException;
    String getDefaultImage() throws ServiceException;

    /**
     * Delete all photos related with this apartment id
     * @param apartmentId apartment id
     * @return true if photos were deleted, false if not
     * @throws ServiceException if DaoException thrown
     */
    boolean deleteAllApartmentPhotos(long apartmentId) throws ServiceException;

    /**
     * Delete photo by photo id
     * @param id photo id
     * @return true if photo was deleted, false if not
     * @throws ServiceException if DaoException thrown
     */
    boolean deletePhotoById(long id) throws ServiceException;
}
