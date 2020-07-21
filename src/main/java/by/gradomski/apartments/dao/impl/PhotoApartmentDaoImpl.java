package by.gradomski.apartments.dao.impl;

import by.gradomski.apartments.dao.PhotoApartmentDao;
import by.gradomski.apartments.dao.column.UserTable;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PhotoApartmentDaoImpl implements PhotoApartmentDao {
    private static final Logger log = LogManager.getLogger();
    private static final String ID_APARTMENT_COLUMN = "idApartment";
    private static final String PHOTO_COLUMN = "photo";
    private static final String INSERT_NEW_PHOTO = "INSERT INTO apartmentphotos (idApartment, photo) VALUES (?, ?)";
    private static final String SELECT_PHOTO_BY_APARTMENT_ID = "SELECT photo FROM apartmentphotos WHERE idApartment=?";
    private static final String DELETE_PHOTOS_BY_APARTMENT_ID = "DELETE photo FROM apartmentphotos WHERE idApartment=?";
    private static final String SELECT_DEFAULT_PHOTO = "SELECT photo FROM apartmentphotos WHERE idApartment=0";

    private static final String SELECT_USER_BY_LOGIN = "SELECT idUser, idRole, login, password, firstName, lastName, birthday, gender, phone," +
            " photo, registrationDate, mailAddress, visibility FROM user WHERE login=?";
    private static final String UPDATE_USER_BY_LOGIN = "UPDATE user SET firstName=?, lastName=?, birthday=?, gender=?, phone=? WHERE login=?";
    private static final String UPDATE_PHOTO_BY_LOGIN = "UPDATE user SET photo=? WHERE login=?";
    private static final String UPDATE_USER_VISIBILITY_BY_LOGIN = "UPDATE user SET visibility=? WHERE login=?";
    private static final String DEFAULT_PHOTO_PATH = "F:\\My Projects\\epam java training\\Appartment project\\testPhotos\\apartment\\def_apartment.jpg";
    private static PhotoApartmentDaoImpl instance;

    private PhotoApartmentDaoImpl(){}

    public static PhotoApartmentDaoImpl getInstance(){
        if(instance == null){
            instance = new PhotoApartmentDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(InputStream inputStream, long apartmentId) throws DaoException{
        boolean flag = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_NEW_PHOTO);
            statement.setLong(1, apartmentId);
            statement.setBinaryStream(2, inputStream, inputStream.available());
            int row = statement.executeUpdate();
            if(row > 0){
                flag = true;
            }
        } catch (SQLException | IOException e){
            throw new DaoException(e);
        } finally {
            close(inputStream);
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return flag;
    }

    @Override
    public List<String> findByApartment(long apartmentId) throws DaoException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        List<String> photoList = new ArrayList<>();;
        try {
            statement = connection.prepareStatement(SELECT_PHOTO_BY_APARTMENT_ID);
            statement.setLong(1, apartmentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                byte[] photoBytes = resultSet.getBytes(PHOTO_COLUMN);
                String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
                photoList.add(photoBase64);
            }
        } catch (SQLException e){
                throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return photoList;
    }

    @Override
    public String findDefaultImage() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        String defaultImage = null;
        try {
            statement = connection.prepareStatement(SELECT_DEFAULT_PHOTO);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                byte[] photoBytes = resultSet.getBytes(PHOTO_COLUMN);
                defaultImage = Base64.getEncoder().encodeToString(photoBytes);
            }
        } catch (SQLException e){
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return defaultImage;
    }

    @Override
    public boolean deletePhotoById(long id) throws DaoException{
        return false;
    }

    @Override
    public boolean deletePhotosByApartmentId(long apartmentId) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        boolean flag = false;
        try {
            statement = connection.prepareStatement(DELETE_PHOTOS_BY_APARTMENT_ID);
            statement.setLong(1, apartmentId);
            int rows = statement.executeUpdate();
            if(rows != 0){
                flag = true;
            }
        }catch (SQLException e){
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return flag;
    }

    private void close(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.error("inputStream can't be closed: " + inputStream);
        }
    }
}
