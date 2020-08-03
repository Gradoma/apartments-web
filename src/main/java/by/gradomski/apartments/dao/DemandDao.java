package by.gradomski.apartments.dao;

import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.entity.DemandStatus;
import by.gradomski.apartments.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface DemandDao extends BaseDao {
    boolean add(Demand demand) throws DaoException;
    List<Demand> findAll() throws DaoException;
    Optional<Demand> findById(long id) throws DaoException;
    List<Demand> findByApplicant(long id) throws DaoException;
    List<Demand> findByApartment(long id) throws DaoException;
    Demand update(Demand demand) throws DaoException;
    boolean updateStatusById(long id, DemandStatus status) throws DaoException;
}
