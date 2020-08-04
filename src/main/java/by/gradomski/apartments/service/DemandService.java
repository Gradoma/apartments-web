package by.gradomski.apartments.service;

import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;

import java.util.List;

/**
 * Service interface for handle Demand entities{@link Demand}
 */
public interface DemandService {

    /**
     * Create new Demand, set parameters to demand, and pass to Dao layer
     * @param author user will be set as author of demand
     * @param apartmentIdString long will be set as apartment id for this demand
     * @param expectedDateString string value, should be parsed to LocalDate
     * @param description string description
     * @return true if demand was added to database, false if not
     * @throws ServiceException if DaoException thrown
     */
    boolean addDemand(User author, String apartmentIdString, String expectedDateString,
                      String description) throws ServiceException;

    /**
     * Get list of all demands from database
     * @return ArrayList with all demands, empty if no demands in database
     * @throws ServiceException if DaoException thrown
     */
    List<Demand> getAll() throws ServiceException;

    /**
     * Get demand by demand id
     * @param id demand id
     * @return demand if was found
     * @throws ServiceException if DaoException thrown or demand wasn't found
     */
    Demand getDemandById(long id) throws ServiceException;

    /**
     * Get list of demands created for apartment with this id and where demand status not DELETED
     * @param apartmentId id
     * @return ArrayList with all demands, empty if no demands in database
     * @throws ServiceException if DaoException thrown or demand wasn't found
     */
    List<Demand> getActiveDemandsByApartmentId(long apartmentId) throws ServiceException;

    /**
     * Get list of demands created by user with this id and where demand status not ARCHIVED
     * @param applicantId user id
     * @return ArrayList with all demands, empty if no demands in database
     * @throws ServiceException if DaoException thrown or demand wasn't found
     */
    List<Demand> getDemandsByApplicantId(long applicantId) throws ServiceException;

    /**
     * Check if demand list contains demand with this id, update demand with this id status to APPROVED
     * @param approvingRequestId id of demand will be updated
     * @param apartmentDemandList list of all demands created for apartment
     * @return false if demand wasn't updated or demand wasn't found by id in list, otherwise true
     * @throws ServiceException if DaoException thrown or demand wasn't found
     */
    boolean approveDemandFromList(long approvingRequestId, List<Demand> apartmentDemandList) throws ServiceException;

    /**
     * Update demand status, set REFUSED
     * @param id demand id
     * @return true if demand status was updated, false if not
     * @throws ServiceException if DaoException thrown or demand wasn't found
     */
    boolean refuseDemand(long id) throws ServiceException;

    /**
     * Update demand status, set CANCEL
     * @param id demand id
     * @return true if demand status was updated, false if not
     * @throws ServiceException if DaoException thrown or demand wasn't found
     */
    boolean cancelDemand(long id) throws ServiceException;

    /**
     * Update demand status, set ARCHIVED
     * @param id demand id
     * @return true if demand status was updated, false if not
     * @throws ServiceException if DaoException thrown or demand wasn't found
     */
    boolean archiveDemand(long id) throws ServiceException;
}
