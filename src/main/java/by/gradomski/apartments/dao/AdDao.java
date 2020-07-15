package by.gradomski.apartments.dao;

import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface AdDao extends BaseDao {
    long add(Ad ad) throws DaoException;
    Optional<Ad> findById(long id) throws DaoException;
    List<Ad> findAll() throws DaoException;
    List<Ad> findAllVisible() throws DaoException;
    List<Ad> findByAuthor(long id) throws DaoException;
    Ad update(Ad ad) throws DaoException;
    boolean deleteById(long id) throws DaoException;
}
