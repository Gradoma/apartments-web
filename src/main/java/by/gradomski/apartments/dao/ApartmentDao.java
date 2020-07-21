package by.gradomski.apartments.dao;

import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.ApartmentStatus;
import by.gradomski.apartments.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface ApartmentDao extends BaseDao {
    long add(Apartment apartment) throws DaoException;
    Optional<Apartment> findApartmentByIdWithOwner(long id) throws DaoException;
    List<Apartment> findApartmentsByOwner(long id) throws DaoException;
    List<Apartment> findApartmentsByTenant(long id) throws DaoException;
    List<Apartment> findAll() throws DaoException;
    void update(Apartment apartment) throws DaoException;
    ApartmentStatus findStatusByApartmentId(long id) throws DaoException;
    boolean updateStatusByApartmentId(long id, ApartmentStatus status) throws DaoException;
    boolean updateTenantByApartmentId(long id, long tenantId) throws DaoException;
}
