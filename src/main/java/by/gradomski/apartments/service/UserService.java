package by.gradomski.apartments.service;

import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Service interface for handle Advertisement entities{@link User}
 */
public interface UserService {

    /**
     * Check parameters, if parameters valid - try to find user with this login. If such user wasn't found
     * create new User and pass it to the Dao layer().
     * User Role will be set as USER.
     * @param login new user's login
     * @param password new user's password
     * @param email new user's email
     * @return HashMap<String, String> of validation result; if any parameter fails validation - put(parameterName, "FALSE")
     * @throws ServiceException if DaoException was thrown
     */
    Map<String, String> signUp(String login, String password, String email) throws ServiceException;

    /**
     * Check parameters, if parameters valid - try to find user with this login. If such user wasn't found
     * create new User and pass it to the Dao layer().
     * User Role will be set as ADMIN.
     * @param login new user's login
     * @param password new user's password
     * @param email new user's email
     * @return HashMap<String, String> of validation result; if any parameter fails validation - put(parameterName, "FALSE")
     * @throws ServiceException if DaoException was thrown
     */
    Map<String, String> createNewAdmin(String login, String password, String email) throws ServiceException;

    /**
     * Check parameters, if valid - try to find user by login. If user was found - check for equality parameter
     * password and password from user object.
     * @param login user login
     * @param password user password
     * @return true if parameters are valid, user with this login is found, passwords are equal; otherwise false
     * @throws ServiceException if DaoException was thrown
     */
    boolean signIn(String login, String password) throws ServiceException;

    /**
     * Update user visibility, set visibility true
     * @param login user login
     * @throws ServiceException if DaoException was thrown
     */
    void activateUser(String login) throws ServiceException;

    /**
     * Get list of all users
     * @return ArrayList of users, empty if no users
     * @throws ServiceException if DaoException was thrown
     */
    List<User> getAll() throws ServiceException;

    /**
     * Get user by user login
     * @param login string user login
     * @return user object
     * @throws ServiceException if DaoException was thrown or user wasn't found by login
     */
    User getUserByLogin(String login) throws ServiceException;

    /**
     *  Get user by user id
     * @param id long user id
     * @return user object
     * @throws ServiceException if DaoException was thrown or user wasn't found by login
     */
    User getUserById(long id) throws ServiceException;

    /**
     * Create new User object, set parameters, update this user in database
     * @param login user login
     * @param gender user gender, should be converted to enum Role {@link by.gradomski.apartments.entity.Gender}
     * @param firstName user first name
     * @param lastName user last name
     * @param phone user phone
     * @param birthday string birthday, should be parsed to LocalDate
     * @return updated user from database
     * @throws ServiceException if DaoException was thrown or user wasn't found by login after updating or birthday
     * parsing threw DateTimeParseException
     */
    User updateUser(String login, String gender, String firstName,
                    String lastName, String phone, String birthday) throws ServiceException;

    /**
     * Find user by login and update his photo
     * @param inputStream photo represented in inputStream, not null
     * @param login user login
     * @return updated user with this login
     * @throws ServiceException if DaoException was thrown or user wasn't found by login after updating or inputStream
     * is null
     */
    User updateUserPhoto(InputStream inputStream, String login) throws ServiceException;
}
