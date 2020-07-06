package by.gradomski.apartments.dao;

import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.exception.DaoException;

import java.util.List;

public interface ApartmentDao extends BaseDao {
    boolean add(Apartment apartment) throws DaoException;
    List<Apartment> findApartmentsByOwner(long id) throws DaoException;
    List<Apartment> findApartmentsByTenant(long id) throws DaoException;
    List<Apartment> findAll() throws DaoException;
    Apartment update(Apartment apartment) throws DaoException;
    boolean deleteApartmentById(long id) throws DaoException;
}
