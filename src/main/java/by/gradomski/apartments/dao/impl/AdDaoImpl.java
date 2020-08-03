package by.gradomski.apartments.dao.impl;

import by.gradomski.apartments.dao.AdDao;
import by.gradomski.apartments.dao.column.AdTable;
import by.gradomski.apartments.entity.Advertisement;
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
import java.util.Optional;

public class AdDaoImpl implements AdDao {
    private static final Logger log = LogManager.getLogger();
    private static AdDaoImpl instance;
    private static final String INSERT_NEW_AD = "INSERT INTO ad (title, price, idAuthor, idAppartment, issueDate) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT idAd, title, price, idAuthor, idAppartment, issueDate, visibility" +
            " FROM ad";
    private static final String SELECT_AD_BY_ID = "SELECT title, price, idAuthor, idAppartment, issueDate, visibility" +
            " FROM ad WHERE idAd=?";
    private static final String SELECT_AD_BY_APARTMENT_ID = "SELECT idAd, title, price, idAuthor, issueDate, visibility" +
            " FROM ad WHERE idAppartment=?";
    private static final String SELECT_ALL_VISIBLE_AD = "SELECT idAd, title, price, idAuthor, idAppartment, issueDate" +
            " FROM ad WHERE visibility=1";
    private static final String UPDATE_AD_BY_ID = "UPDATE ad SET title=?, price=?, visibility=? WHERE idAd=?";
    private static final String DELETE_AD_BY_ID = "DELETE FROM ad WHERE idAd=?";

    private AdDaoImpl(){}

    public static AdDaoImpl getInstance(){
        if(instance == null){
            instance = new AdDaoImpl();
        }
        return instance;
    }

    @Override
    public long add(Advertisement advertisement) throws DaoException{
        long key = -1;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_NEW_AD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, advertisement.getTitle());
            statement.setBigDecimal(2, advertisement.getPrice());
            statement.setLong(3, advertisement.getAuthorId());
            statement.setLong(4, advertisement.getApartmentId());
            Instant instant = advertisement.getCreationDate().atZone(ZoneId.systemDefault()).toInstant();
            long creationMillis = instant.toEpochMilli();
            statement.setLong(5, creationMillis);
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
    public Optional<Advertisement> findById(long id) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        Optional<Advertisement> optionalAd = Optional.empty();
        try{
            statement = connection.prepareStatement(SELECT_AD_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Advertisement advertisement = null;
            while (resultSet.next()){
                String title = resultSet.getString(AdTable.TITLE);
                BigDecimal price = new BigDecimal(resultSet.getString(AdTable.PRICE));
                long apartmentId = resultSet.getLong(AdTable.ID_APARTMENT);
                long authorId = resultSet.getLong(AdTable.ID_AUTHOR);
                advertisement = new Advertisement(title, authorId, price, apartmentId);
                advertisement.setId(id);
                long creationMillis = resultSet.getLong(AdTable.ISSUE_DATE);
                LocalDateTime creationDate = Instant.ofEpochMilli(creationMillis).atZone(ZoneId.systemDefault()).
                        toLocalDateTime();
                advertisement.setCreationDate(creationDate);
                advertisement.setVisibility(resultSet.getBoolean(AdTable.VISIBILITY));
            }
            if(advertisement != null){
                optionalAd = Optional.of(advertisement);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return optionalAd;
    }

    @Override
    public List<Advertisement> findAll() throws DaoException{
        List<Advertisement> listAdvertisement = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        Statement statement = null;
        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()){
                String title = resultSet.getString(AdTable.TITLE);
                long authorId = resultSet.getLong(AdTable.ID_AUTHOR);
                BigDecimal price = new BigDecimal(resultSet.getString(AdTable.PRICE));
                long apartmentId = resultSet.getLong(AdTable.ID_APARTMENT);
                Advertisement advertisement = new Advertisement(title, authorId, price, apartmentId);
                advertisement.setId(resultSet.getLong(AdTable.ID_AD));
                long creationMillis = resultSet.getLong(AdTable.ISSUE_DATE);
                LocalDateTime creationDate = Instant.ofEpochMilli(creationMillis).atZone(ZoneId.systemDefault()).
                        toLocalDateTime();
                advertisement.setCreationDate(creationDate);
                advertisement.setVisibility(resultSet.getBoolean(AdTable.VISIBILITY));
                listAdvertisement.add(advertisement);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return listAdvertisement;
    }

    @Override
    public List<Advertisement> findAllVisible() throws DaoException {
        List<Advertisement> listAdvertisement = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_ALL_VISIBLE_AD);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String title = resultSet.getString(AdTable.TITLE);
                long authorId = resultSet.getLong(AdTable.ID_AUTHOR);
                BigDecimal price = new BigDecimal(resultSet.getString(AdTable.PRICE));
                long apartmentId = resultSet.getLong(AdTable.ID_APARTMENT);
                Advertisement advertisement = new Advertisement(title, authorId, price, apartmentId);
                advertisement.setId(resultSet.getLong(AdTable.ID_AD));
                long creationMillis = resultSet.getLong(AdTable.ISSUE_DATE);
                LocalDateTime creationDate = Instant.ofEpochMilli(creationMillis).atZone(ZoneId.systemDefault()).
                        toLocalDateTime();
                advertisement.setCreationDate(creationDate);
                advertisement.setVisibility(true);
                listAdvertisement.add(advertisement);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return listAdvertisement;
    }

    @Override
    public List<Advertisement> findByAuthor(long id) {
        return null;
    }

    @Override
    public Optional<Advertisement> findByApartmentId(long id) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        Optional<Advertisement> optionalAd = Optional.empty();
        try{
            statement = connection.prepareStatement(SELECT_AD_BY_APARTMENT_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Advertisement advertisement = null;
            while (resultSet.next()){
                advertisement = new Advertisement();
                advertisement.setId(resultSet.getLong(AdTable.ID_AD));
                advertisement.setTitle(resultSet.getString(AdTable.TITLE));
                BigDecimal price = new BigDecimal(resultSet.getString(AdTable.PRICE));
                advertisement.setPrice(price);
                advertisement.setApartmentId(id);
                long creationMillis = resultSet.getLong(AdTable.ISSUE_DATE);
                LocalDateTime creationDate = Instant.ofEpochMilli(creationMillis).atZone(ZoneId.systemDefault()).
                        toLocalDateTime();
                advertisement.setCreationDate(creationDate);
                advertisement.setVisibility(resultSet.getBoolean(AdTable.VISIBILITY));
            }
            if(advertisement != null){
                optionalAd = Optional.of(advertisement);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return optionalAd;
    }

    @Override
    public boolean update(Advertisement advertisement) throws DaoException{
        boolean result = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_AD_BY_ID);
            statement.setString(1, advertisement.getTitle());
            statement.setBigDecimal(2, advertisement.getPrice());
            statement.setBoolean(3, advertisement.isVisible());
            statement.setLong(4, advertisement.getId());
            int rows = statement.executeUpdate();
            if(rows != 0){
                result = true;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return result;
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
