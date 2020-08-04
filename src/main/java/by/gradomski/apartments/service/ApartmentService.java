package by.gradomski.apartments.service;

import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.ApartmentStatus;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Service interface for handle Apartment entities{@link Apartment}
 */
public interface ApartmentService {

    /** Check parameters, create new Apartment and pass it to the Dao layer(if parameters valid)
     *
     * @param owner user entity will be set as ownerId
     * @param region string as region of apartment
     * @param city string as city of apartment
     * @param address string as address of apartment
     * @param rooms number of rooms string value, such as "2"
     * @param floor string as floor value, such as "2"
     * @param square string as square value, such as "65.4" or "65,5"
     * @param year string as year of build value, such as "2002", not later than current year
     * @param furniture string as furniture value, should parsed to boolean by Boolean.parseBoolean(furniture)
     * @param description string as apartment description, length not longer than 200 characters
     * @return HashMap<String, String> of validation result; if any parameter fails validation - put(parameterName, "FALSE")
     * @throws ServiceException
     */
    Map<String, String> addApartment(User owner, String region, String city, String address, String rooms, String floor,
                                     String square, String year, String furniture, String description) throws ServiceException;

    /** Get list of apartments by id of apartment's owner.
     *
     * @param id long owner id
     * @return ArrayList of apartments, empty if no apartments found
     * @throws ServiceException if DaoException thrown
     */
    List<Apartment> getApartmentsByOwner(long id) throws ServiceException;

    List<Apartment> getApartmentsByTenant(long id) throws ServiceException;

    /**
     * Get list of all apartments.
     * @return ArrayList of apartments, empty if no apartments found
     * @throws ServiceException if DaoException thrown
     */
    List<Apartment> getAllApartments() throws ServiceException;

    /**
     * Get apartment by apartment id.
     * <p>
     * Apartment object contains reference to user object as owner, but tenant is null
     * @param id apartment id.
     * @return Apartment object
     * @throws ServiceException if DaoException thrown
     */
    Apartment getApartmentByIdWithOwner(long id) throws ServiceException;

    /**
     * Check parameters, set it to Apartment object and pass it to the Dao layer(if parameters valid)
     * @param id apartment id.
     * @param region string as region of apartment
     * @param city string as city of apartment
     * @param address string as address of apartment
     * @param rooms number of rooms string value, such as "2"
     * @param floor string as floor value, such as "2"
     * @param square string as square value, such as "65.4" or "65,5"
     * @param year string as year of build value, such as "2002", not later than current year
     * @param furniture string as furniture value, should parsed to boolean by Boolean.parseBoolean(furniture)
     * @param description string as apartment description, length not longer than 200 characters
     * @return HashMap<String, String> of validation result; if any parameter fails validation - put(parameterName, "FALSE")
     * @throws ServiceException if DaoException thrown
     */
    Map<String, String> updateApartment(long id, String region, String city, String address, String rooms, String floor,
                                        String square, String year, String furniture, String description) throws ServiceException;

    /**
     * Update status of apartment with this id.
     * @param id apartment id
     * @param status new apartment status
     * @return true if apartment was updated, false if not
     * @throws ServiceException  if DaoException thrown
     */
    boolean updateApartmentStatus(long id, ApartmentStatus status) throws ServiceException;

    /**
     * Update id of tenant in apartment with this apartmentId
     * @param apartmentId apartment id
     * @param tenantId new tenant id
     * @return true if apartment was updated, false if not
     * @throws ServiceException if DaoException thrown
     */
    boolean updateTenant(long apartmentId, long tenantId) throws ServiceException;

    /**
     * Set apartment status DELETED in apartment with this id in database
     * <p>
     *     Also will be deleted every photos from database.
     *     If current apartment status is IN_DEMAND - every demand will be refused,then apartment status will be updated
     *     If current apartment status is RENT or DELETED - nothing happens.
     * @param id apartment id
     * @return true if apartment status was updated, false if not
     * @throws ServiceException
     */
    boolean deleteApartment(long id) throws ServiceException;
}
