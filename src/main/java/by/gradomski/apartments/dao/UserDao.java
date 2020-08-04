package by.gradomski.apartments.dao;

import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.DaoException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Dao interface for handling database information related with User{@link User}
 */
public interface UserDao extends BaseDao {
    /**
     * Insert new user to database
     * @param user will be added to database
     * @return true if object added to database
     * @throws DaoException if SQLException or FileNotFoundException thrown
     */
    boolean add(User user) throws DaoException;

    /**
     * Select all demands which database contains
     * @return ArrayList users, empty if users in database wasn't found
     * @throws DaoException if SQLException or IncorrectRoleException thrown
     */
    List<User> findAll() throws DaoException;

    /**
     * Select user with id equals parameter id
     * @param id parameter id
     * @return Optional contains User, empty if wasn't found
     * @throws DaoException if SQLException or IncorrectRoleException thrown
     */
    Optional<User> findById(long id) throws DaoException;

    /**
     * Select user with login equals parameter login
     * @param login user's login
     * @return Optional contains User, empty if wasn't found
     * @throws DaoException if SQLException or IncorrectRoleException thrown
     */
    Optional<User> findByLogin(String login) throws DaoException;

    /**
     * Replace user in database to parameter user.
     * @param user will be set to database
     * @return true if rows in database were updated, false if not
     * @throws DaoException if SQLException thrown
     */
    boolean update(User user) throws DaoException;

    /**
     * Update user's photo in database
     * @param inputStream photo converted to inputStream
     * @param login user's login
     * @return true if rows in database were updated, false if not
     * @throws DaoException if SQLException thrown
     */
    boolean updatePhoto(InputStream inputStream, String login) throws DaoException;
    boolean deleteById(long id) throws DaoException;

    /**
     * Set user's visibility true where user's login equals parameter login
     * @param login user's login
     * @return true if rows in database were updated, false if not
     * @throws DaoException
     */
    boolean changeVisibilityByLogin(String login) throws DaoException;
}
