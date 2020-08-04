package by.gradomski.apartments.dao;

import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.entity.DemandStatus;
import by.gradomski.apartments.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * Dao interface for handling database information related with Demands{@link Demand}
 */
public interface DemandDao extends BaseDao {

    /**
     * Insert new demand to database
     * @param demand will be added to database
     * @return true if database rows was updated, false if not
     * @throws DaoException if SQLException thrown
     */
    boolean add(Demand demand) throws DaoException;

    /**
     * Select all demands which database contains
     * @return ArrayList demands, empty if demands in database wasn't found
     * @throws DaoException
     */
    List<Demand> findAll() throws DaoException;

    /**
     * Select demand with id equals parameter id
     * @param id demand id
     * @return Optional contains Demands, empty if wasn't found
     * @throws DaoException if SQLException or IncorrectStatusException
     * {@link by.gradomski.apartments.exception.IncorrectStatusException} thrown
     */
    Optional<Demand> findById(long id) throws DaoException;

    /**
     * Select demands where applicant has id equals parameter id
     * @param id applicant id
     * @return ArrayList demands, empty if demands in database wasn't found
     * @throws DaoException if SQLException or IncorrectStatusException
     * {@link by.gradomski.apartments.exception.IncorrectStatusException} thrown
     */
    List<Demand> findByApplicant(long id) throws DaoException;

    /**
     * Select demands where apartment has id equals parameter id
     * @param id apartment id
     * @return ArrayList demands, empty if demands in database wasn't found
     * @throws DaoException if SQLException or IncorrectStatusException
     * {@link by.gradomski.apartments.exception.IncorrectStatusException} thrown
     */
    List<Demand> findByApartment(long id) throws DaoException;

    /**
     * Replace demand in database to parameter demand
     * @param demand will be set to database
     * @return demand transferred as demand parameter
     * @throws DaoException if SQLException thrown
     */
    Demand update(Demand demand) throws DaoException;

    /**
     * Replace status of demand with id equals parameter id
     * @param id demand id
     * @param status new status {@link DemandStatus}
     * @return true if rows in database was updated, false if not
     * @throws DaoException if SQLException thrown
     */
    boolean updateStatusById(long id, DemandStatus status) throws DaoException;
}
