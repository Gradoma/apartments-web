package by.gradomski.apartments.dao;

import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * Dao interface for handling database information related with Advertisement{@link Advertisement}
 */
public interface AdDao extends BaseDao {

    /**
     * Insert new advertisement to database
     * @param advertisement will be added to database
     * @return generated id of advertisement in database
     * @throws DaoException if SQLException thrown
     */
    long add(Advertisement advertisement) throws DaoException;

    /**
     * Select advertisement in database by advertisement id
     * @param id advertisement id
     * @return Optional contains Advertisement, empty if wasn't found
     * @throws DaoException  if SQLException thrown
     */
    Optional<Advertisement> findById(long id) throws DaoException;

    /**
     * Select all advertisements which database contains
     * @return ArrayList with Advertisements, empty if database doesn't contains any advertisement
     * @throws DaoException  if SQLException thrown
     */
    List<Advertisement> findAll() throws DaoException;

    /**
     * Select advertisements from database with visibility = true
     * @return ArrayList with Advertisements, empty if database doesn't contains advertisements with visibility = true
     * @throws DaoException if SQLException thrown
     */
    List<Advertisement> findAllVisible() throws DaoException;

    /**
     * Select advertisements from database where author has id equal parameter id
     * @param id id of author of advertisement
     * @return ArrayList with Advertisements
     * @throws DaoException if SQLException thrown
     */
    List<Advertisement> findByAuthor(long id) throws DaoException;

    /**
     * Select advertisements from database where apartment has id equal parameter id
     * @param id apartment id
     * @return Optional contains Advertisement, empty if wasn't found
     * @throws DaoException if SQLException thrown
     */
    Optional<Advertisement> findByApartmentId(long id) throws DaoException;

    /**
     * Update advertisement in database. Can update advertisement title, price, visible only
     * @param advertisement will be input to database
     * @return true if database rows was updated, false if not
     * @throws DaoException if SQLException thrown
     */
    boolean update(Advertisement advertisement) throws DaoException;

    /**
     * Delete advertisement with id equals parameter id from database
     * @param id advertisement id
     * @return true if database row was deleted, false if not
     * @throws DaoException
     */
    boolean deleteById(long id) throws DaoException;
}
