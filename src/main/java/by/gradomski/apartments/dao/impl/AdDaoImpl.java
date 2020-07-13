package by.gradomski.apartments.dao.impl;

import by.gradomski.apartments.dao.AdDao;
import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public class AdDaoImpl implements AdDao {
    private static final Logger log = LogManager.getLogger();
    private static AdDaoImpl instance;
    private static final String INSERT_NEW_AD = "INSERT INTO ad (title, price, idAuthor, idAppartment, issueDate," +
            "visibility) VALUES (?, ?, ?, ?, ?, ?)";

    private AdDaoImpl(){}

    public static AdDaoImpl getInstance(){
        if(instance == null){
            instance = new AdDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Ad ad) throws DaoException{
        boolean flag = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_NEW_AD);
            statement.setString(1, ad.getTitle());
            statement.setBigDecimal(2, ad.getPrice());
            statement.setLong(3, ad.getAuthor().getId());
            statement.setLong(4, ad.getApartmentId());
            Instant instant = ad.getCreationDate().atZone(ZoneId.systemDefault()).toInstant();
            long creationMillis = instant.toEpochMilli();
            statement.setLong(5, creationMillis);
            statement.setBoolean(6, ad.isVisible());
            int rows = statement.executeUpdate();
            if(rows == 0){
                log.warn("table apartment wasn't updated, check database");
            } else {
                flag = true;
            }
        } catch (SQLException e){
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return flag;
    }

    @Override
    public List<Ad> findAll() {
        return null;
    }

    @Override
    public List<Ad> findByAuthor(long id) {
        return null;
    }

    @Override
    public Ad update(Ad ad) {
        return null;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }
}
