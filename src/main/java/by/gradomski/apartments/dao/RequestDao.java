package by.gradomski.apartments.dao;

import by.gradomski.apartments.entity.Request;
import by.gradomski.apartments.exception.DaoException;

import java.util.List;

public interface RequestDao extends AbstractDao {
    boolean add(Request request) throws DaoException;
    List<Request> findAll() throws DaoException;
    List<Request> findByApplicant(long id) throws DaoException;
    List<Request> findByApartment(long id) throws DaoException;
    Request update(Request request) throws DaoException;
}
