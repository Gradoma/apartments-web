package by.gradomski.apartments.dao;

import by.gradomski.apartments.exception.DaoException;

import java.io.InputStream;
import java.util.Map;

public interface PhotoApartmentDao extends BaseDao {
    boolean add(InputStream inputStream, long apartmentId) throws DaoException;
    Map<Long, String> findByApartment(long apartmentId) throws DaoException;
    String findDefaultImage() throws DaoException;
    boolean deletePhotoById(long id) throws DaoException;
    boolean deletePhotosByApartmentId(long apartmentId) throws DaoException;
}
