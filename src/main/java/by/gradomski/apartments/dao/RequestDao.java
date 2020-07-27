package by.gradomski.apartments.dao;

import by.gradomski.apartments.entity.Request;
import by.gradomski.apartments.entity.RequestStatus;
import by.gradomski.apartments.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface RequestDao extends BaseDao {
    boolean add(Request request) throws DaoException;
    List<Request> findAll() throws DaoException;
    Optional<Request> findById(long id) throws DaoException;
    List<Request> findByApplicant(long id) throws DaoException;
    List<Request> findByApartment(long id) throws DaoException;
    Request update(Request request) throws DaoException;
    boolean updateStatusById(long id, RequestStatus status) throws DaoException;
}
