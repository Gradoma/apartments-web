package by.gradomski.apartments.dao;

import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.exception.DaoException;

import java.io.InputStream;
import java.util.Map;

/**
 * Dao interface for handling database information related with apartment photos
 */
public interface PhotoApartmentDao extends BaseDao {

    /**
     * Add photo to database related with apartment with id equals apartmentId
     * @param inputStream photo converted to inputStream
     * @param apartmentId id of apartment
     * @return true if database rows were updated, false if not
     * @throws DaoException if SQLException or IOException thrown
     */
    boolean add(InputStream inputStream, long apartmentId) throws DaoException;

    /**
     * Select photos and photos id from database related with apartment with id equals parameter apartmentId
     * @param apartmentId id of apartment
     * @return HashMap where Long - photoId, String - photo converted to base64 String, empty if photos weren't found
     * @throws DaoException if SQLException thrown
     */
    Map<Long, String> findByApartment(long apartmentId) throws DaoException;

    /**
     * Get default photo from database
     * @return photo converted to base64 String
     * @throws DaoException if SQLException thrown
     */
    String findDefaultImage() throws DaoException;

    /**
     * Delete photo from database where id of photo equals parameter id
     * @param id id of photo
     * @return true if rows in database were updated, false if not
     * @throws DaoException if SQLException thrown
     */
    boolean deletePhotoById(long id) throws DaoException;

    /**
     * Delete all photos related with apartment with id equals parameter apartmentId
     * @param apartmentId id of apartment
     * @return true if rows in database were updated, false if not
     * @throws DaoException if SQLException thrown
     */
    boolean deletePhotosByApartmentId(long apartmentId) throws DaoException;
}
