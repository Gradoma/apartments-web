package by.gradomski.apartments.service;

import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;

import java.util.List;

/**
 * Service interface for handle Advertisement entities{@link Advertisement}
 */
public interface AdService {
    /** Create new Advertisement object from parameters and pass to Dao layer
     *
     * @param title advertisement title
     * @param author user, creator of advertisement
     * @param price advertisement title
     * @param apartmentId id oa apartment indicated in the advertisement
     * @return long generated id of advertisement added to database
     * @throws ServiceException if DaoException thrown
     */
    long addAdvertisement(String title, User author, String price, long apartmentId) throws ServiceException;

    /** Get list of all advertisements contain in database
     *
     * @return ArrayList of all advertisements, empty if no advertisements
     * @throws ServiceException
     */
    List<Advertisement> getAll() throws ServiceException;

    /** Get advertisement by id of apartment, indicated in the advertisement
     *
     * @param id id of apartment, indicated in the advertisement
     * @return Advertisement object
     * @throws ServiceException if DaoException thrown or advertisement wasn't found
     */
    Advertisement getAdByApartmentId(long id) throws ServiceException;

    /** Get advertisement by advertisement id
     *
     * @param id id of advertisement
     * @return Advertisement object
     * @throws ServiceException if DaoException thrown or advertisement wasn't found
     */
    Advertisement getAdById(long id) throws ServiceException;

    /** Get all advertisements where visibility = true
     *
     * @return ArrayList of all advertisements, empty if no advertisements
     * @throws ServiceException
     */
    List<Advertisement> getAllVisible() throws ServiceException;

    /** Set parameters title and price to advertisement object and pass to Dao layer
     *
     * @param advertisement advertisement for update, not null
     * @param title new title
     * @param price new price
     * @return true if advertisement updated, false if not
     * @throws ServiceException if DaoException thrown
     */
    boolean updateAd(Advertisement advertisement, String title, String price) throws ServiceException;

    /** Update advertisement by advertisement id
     *
     * @param id advertisement id
     * @return true advertisement updated, false if not
     * @throws ServiceException
     */
    boolean changeVisibility(long id) throws ServiceException;

    /** Delete advertisement with this id from database
     *
     * @param id advertisement id
     * @return  true advertisement deleted from database, false if not
     * @throws ServiceException
     */
    boolean deleteAd(long id) throws ServiceException;
}
