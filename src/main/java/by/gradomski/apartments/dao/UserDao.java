package by.gradomski.apartments.dao;

import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao {
    boolean add(User user) throws DaoException;
    List<User> findAll() throws DaoException;
    Optional<User> findById(long id) throws DaoException;
    Optional<User> findByLogin(String login) throws DaoException;
    User update(User user) throws DaoException;
    boolean deleteById(long id) throws DaoException;
    boolean changeVisibilityByLogin(String login) throws DaoException;
}
