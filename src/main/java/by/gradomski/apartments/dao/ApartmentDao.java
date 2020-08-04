package by.gradomski.apartments.dao;

import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.ApartmentStatus;
import by.gradomski.apartments.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * Dao interface for handling database information related with Apartment{@link Apartment}
 */
public interface ApartmentDao extends BaseDao {

    /**
     * Insert new apartment to database
     * @param apartment will be added to database
     * @return generated id of apartment in database
     * @throws DaoException if SQLException thrown
     */
    long add(Apartment apartment) throws DaoException;

    /**
     * Select apartment in database by apartment id. Join table user and apartment, create user object and add it as
     * owner of apartment.
     * @param id apartment id
     * @return Optional contains Apartment, empty if wasn't found
     * @throws DaoException if SQLException or IncorrectStatusException
     * {@link by.gradomski.apartments.exception.IncorrectStatusException} or IncorrectRoleException
     * {@link by.gradomski.apartments.exception.IncorrectRoleException} thrown
     */
    Optional<Apartment> findApartmentByIdWithOwner(long id) throws DaoException;

    /**
     * Select apartments where owner has id equals parameter id
     * Join table user and apartment, create user object and add it as
     * tenant of every apartment was found.
     * @param id id of owner
     * @return ArrayList apartments, empty if apartments with specified owner's id wasn't found
     * @throws DaoException if SQLException or IncorrectStatusException
     * {@link by.gradomski.apartments.exception.IncorrectStatusException} or IncorrectRoleException
     * {@link by.gradomski.apartments.exception.IncorrectRoleException} thrown
     */
    List<Apartment> findApartmentsByOwner(long id) throws DaoException;

    /**
     * Select apartments where tenant has id equals parameter id
     * Join table user and apartment, create user object and add it as
     * owner of every apartment was found.
     * @param id id of tenant
     * @return ArrayList apartments, empty if apartments with specified tenant's id wasn't found
     * @throws DaoException if SQLException or IncorrectStatusException
     * {@link by.gradomski.apartments.exception.IncorrectStatusException} or IncorrectRoleException
     * {@link by.gradomski.apartments.exception.IncorrectRoleException} thrown
     */
    List<Apartment> findApartmentsByTenant(long id) throws DaoException;

    /**
     * Select all apartments which database contains
     * @return ArrayList apartments, empty if apartments in database wasn't found
     * @throws DaoException if SQLException or IncorrectStatusException
     * {@link by.gradomski.apartments.exception.IncorrectStatusException} thrown
     */
    List<Apartment> findAll() throws DaoException;

    /**
     * Update apartment in database.
     * @param apartment updated object, will be added to database
     * @throws DaoException if SQLException thrown
     */
    void update(Apartment apartment) throws DaoException;

    /**
     * Get status of apartment with id equals parameter id
     * @param id apartment id
     * @return status of apartment {@link ApartmentStatus}
     * @throws DaoException if SQLException or IncorrectStatusException
     * {@link by.gradomski.apartments.exception.IncorrectStatusException} thrown
     */
    ApartmentStatus findStatusByApartmentId(long id) throws DaoException;

    /**
     * Update status of apartment with id equals parameter id
     * @param id apartment id
     * @param status will be set in database {@link ApartmentStatus}
     * @return true if database rows was updated, false if not
     * @throws DaoException if SQLException thrown
     */
    boolean updateStatusByApartmentId(long id, ApartmentStatus status) throws DaoException;

    /**
     * Update id of tenant of apartment with id equals parameter id in database
     * @param id apartment id
     * @param tenantId will be set in database
     * @return true if database row where updated, false if not
     * @throws DaoException if SQLException thrown
     */
    boolean updateTenantByApartmentId(long id, long tenantId) throws DaoException;
}
