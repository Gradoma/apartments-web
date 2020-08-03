package by.gradomski.apartments.dao;

import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface AdDao extends BaseDao {
    long add(Advertisement advertisement) throws DaoException;
    Optional<Advertisement> findById(long id) throws DaoException;
    List<Advertisement> findAll() throws DaoException;
    List<Advertisement> findAllVisible() throws DaoException;
    List<Advertisement> findByAuthor(long id) throws DaoException;
    Optional<Advertisement> findByApartmentId(long id) throws DaoException;
    boolean update(Advertisement advertisement) throws DaoException;
    boolean deleteById(long id) throws DaoException;
}
