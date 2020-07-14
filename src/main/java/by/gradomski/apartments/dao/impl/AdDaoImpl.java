package by.gradomski.apartments.dao.impl;

import by.gradomski.apartments.dao.AdDao;
import by.gradomski.apartments.dao.column.AdTable;
import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class AdDaoImpl implements AdDao {
    private static final Logger log = LogManager.getLogger();
    private static AdDaoImpl instance;
    private static final String INSERT_NEW_AD = "INSERT INTO ad (title, price, idAuthor, idAppartment, issueDate," +
            "visibility) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_VISIBLE = "SELECT idAd, title, price, idAuthor, idAppartment, issueDate" +
            " FROM ad WHERE visibility=1";
    private static final String DELETE_AD_BY_ID = "DELETE FROM ad WHERE idAd=?";

    private AdDaoImpl(){}

    public static AdDaoImpl getInstance(){
        if(instance == null){
            instance = new AdDaoImpl();
        }
        return instance;
    }

    @Override
    public long add(Ad ad) throws DaoException{
        long key = -1;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_NEW_AD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, ad.getTitle());
            statement.setBigDecimal(2, ad.getPrice());
            statement.setLong(3, ad.getAuthor().getId());
            statement.setLong(4, ad.getApartmentId());
            Instant instant = ad.getCreationDate().atZone(ZoneId.systemDefault()).toInstant();
            long creationMillis = instant.toEpochMilli();
            statement.setLong(5, creationMillis);
            statement.setBoolean(6, ad.isVisible());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                key = resultSet.getLong(1);
            }
        } catch (SQLException e){
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return key;
    }

    @Override
    public List<Ad> findAll() {
        return null;
    }

    @Override
    public List<Ad> findAllVisible() throws DaoException {
        List<Ad> listAd = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null; // TODO(usual statement)
        try{
            statement = connection.prepareStatement(SELECT_ALL_VISIBLE);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String title = resultSet.getString(AdTable.TITLE);
                long authorId = resultSet.getLong(AdTable.ID_AUTHOR);
                BigDecimal price = new BigDecimal(resultSet.getString(AdTable.PRICE));
                long apartmentId = resultSet.getLong(AdTable.ID_APARTMENT);
                Ad ad = new Ad(title,null, price, apartmentId);
                ad.setId(resultSet.getLong(AdTable.ID_AD));
                long creationMillis = resultSet.getLong(AdTable.ISSUE_DATE);
                LocalDateTime creationDate = Instant.ofEpochMilli(creationMillis).atZone(ZoneId.systemDefault()).
                        toLocalDateTime();
                ad.setCreationDate(creationDate);
                listAd.add(ad);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return listAd;
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
    public boolean deleteById(long id) throws DaoException{
        boolean result;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_AD_BY_ID);
            statement.setLong(1, id);
            int rows = statement.executeUpdate();
            result = rows!=0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return result;
    }
}
