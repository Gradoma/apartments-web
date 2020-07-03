package by.gradomski.apartments.dao.impl;

import by.gradomski.apartments.dao.RequestDao;
import by.gradomski.apartments.dao.impl.constant.ApartmentTable;
import by.gradomski.apartments.dao.impl.constant.RequestTable;
import by.gradomski.apartments.dao.impl.constant.UserTable;
import by.gradomski.apartments.entity.*;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.exception.IncorrectRoleException;
import by.gradomski.apartments.exception.IncorrectStatusException;
import by.gradomski.apartments.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequestDaoImpl implements RequestDao {
    private static final String INSERT_NEW_REQUEST = "INSERT INTO request (idApplicant, idApartment, expectedDate, description," +
            " creationDate) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_REQUESTS = "SELECT idRequest, idApplicant, idApartment, expectedDate, creationDate," +
            " request.description, idStatusReq, idUser, idRole, login, password, firstName, lastName, birthday, gender, phone," +
            "user.registrationDate, mailAddress, idAppartment, region, city, address, rooms, square, floor FROM request JOIN user ON idApplicant=idUser JOIN appartment ON idApartment=idAppartment";
    private static final String SELECT_REQUEST_BY_APPLICANT = "SELECT idRequest, idApplicant, idApartment, expectedDate, creationDate," +
            " description, idStatusReq FROM request WHERE idApplicant=?";
    private static final String SELECT_REQUEST_BY_APARTMENT = "SELECT idRequest, idApplicant, idApartment, expectedDate, creationDate," +
            " description, idStatusReq FROM request WHERE idApartment=?";
    private static final String UPDATE_REQUEST = "UPDATE request SET expectedDate=?, description=?, idStatusReq=? WHERE idRequest=?";
    private static RequestDaoImpl instance;

    private RequestDaoImpl(){}

    public static RequestDaoImpl getInstance(){
        if(instance == null){
            instance = new RequestDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Request request) throws DaoException{
        boolean flag = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            //log
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_NEW_REQUEST);
            statement.setLong(1, request.getApplicant().getId());
            statement.setLong(2, request.getApartment().getId());
            Instant expectedInstant = request.getExpectedDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
            long expectedMillis = expectedInstant.toEpochMilli();
            statement.setLong(3, expectedMillis);
            statement.setString(4, request.getDescription());
            Instant creationInstant = request.getCreationDate().atZone(ZoneId.systemDefault()).toInstant();
            long creationMillis = expectedInstant.toEpochMilli();
            statement.setLong(5, creationMillis);
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e){
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return flag;
    }

    @Override
    public List<Request> findAll() throws DaoException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            //log
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        List<Request> requestList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SELECT_ALL_REQUESTS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong(RequestTable.ID_APPLICANT.getValue()));
                user.setRole(Role.getRoleByValue(resultSet.getInt(UserTable.ID_ROLE.getValue())));
                user.setLoginName(resultSet.getString(UserTable.LOGIN.getValue()));
                user.setPassword(resultSet.getString(UserTable.PASSWORD.getValue()));
                user.setFirstName(resultSet.getString(UserTable.FIRST_NAME.getValue()));
                user.setLastName(resultSet.getString(UserTable.LAST_NAME.getValue()));
                long birthdayMillis = resultSet.getLong(UserTable.BIRTHDAY.getValue());
                if(birthdayMillis != 0){
                    LocalDate birthday =
                            Instant.ofEpochMilli(birthdayMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                    user.setBirthday(birthday);
                }
                String gender = resultSet.getString(UserTable.GENDER.getValue());
                if(gender != null){
                    user.setGender(Gender.valueOf(gender));
                }
                user.setPhone(resultSet.getString(UserTable.PHONE.getValue()));
                long registrationMillis = resultSet.getLong(UserTable.REGISTRATION_DATE.getValue());
                LocalDateTime registrationDate = Instant.ofEpochMilli(registrationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                user.setRegistrationDate(registrationDate);
                user.setMail(resultSet.getString(UserTable.MAIL_ADDRESS.getValue()));

                Apartment apartment = new Apartment();
                apartment.setId(resultSet.getLong(ApartmentTable.ID_APARTMENT.getValue()));
                apartment.setRegion(resultSet.getString(ApartmentTable.REGION.getValue()));
                apartment.setCity(resultSet.getString(ApartmentTable.CITY.getValue()));
                apartment.setAddress(resultSet.getString(ApartmentTable.ADDRESS.getValue()));
                apartment.setRooms(resultSet.getInt(ApartmentTable.ROOMS.getValue()));
                apartment.setSquare(resultSet.getDouble(ApartmentTable.SQUARE.getValue()));
                apartment.setFloor(resultSet.getInt(ApartmentTable.FLOOR.getValue()));

                Request request = new Request();
                request.setId(resultSet.getLong(RequestTable.ID_REQUEST.getValue()));
                request.setApplicant(user);
                request.setApartment(apartment);
                long expectedMillis = resultSet.getLong(RequestTable.EXPECTED_DATE.getValue());
                LocalDate expectedDate =
                        Instant.ofEpochMilli(expectedMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                request.setExpectedDate(expectedDate);
                long creationMillis = resultSet.getLong(RequestTable.CREATION_DATE.getValue());
                LocalDateTime creationDate =
                        Instant.ofEpochMilli(creationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                request.setCreationDate(creationDate);
                request.setDescription(resultSet.getString(RequestTable.DESCRIPTION.getValue()));
                long statusId = resultSet.getLong(RequestTable.ID_STATUS_REQUEST.getValue());
                request.setStatus(RequestStatus.getByValue(statusId));
                requestList.add(request);
            }
        }catch (SQLException | IncorrectRoleException | IncorrectStatusException e){
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return requestList;
    }

    @Override
    public List<Request> findByApplicant(long id) throws DaoException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            //log
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        List<Request> requestList = new ArrayList<>();
        try{
            statement = connection.prepareStatement(SELECT_REQUEST_BY_APPLICANT);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Request request = new Request();
                request.setId(resultSet.getLong(RequestTable.ID_REQUEST.getValue()));
                request.setApplicant(resultSet.getObject(RequestTable.ID_APPLICANT.getValue(), User.class));
                request.setApartment(resultSet.getObject(RequestTable.ID_APARTMENT.getValue(), Apartment.class));
                long expectedMillis = resultSet.getLong(RequestTable.EXPECTED_DATE.getValue());
                LocalDate expectedDate =
                        Instant.ofEpochMilli(expectedMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                request.setExpectedDate(expectedDate);
                long creationMillis = resultSet.getLong(RequestTable.CREATION_DATE.getValue());
                LocalDateTime creationDate =
                        Instant.ofEpochMilli(creationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                request.setCreationDate(creationDate);
                request.setDescription(resultSet.getString(RequestTable.DESCRIPTION.getValue()));
                long statusId = resultSet.getLong(RequestTable.ID_STATUS_REQUEST.getValue());
                request.setStatus(RequestStatus.getByValue(statusId));
                requestList.add(request);
            }
        } catch (SQLException | IncorrectStatusException e){
                throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return requestList;
    }

    @Override
    public List<Request> findByApartment(long id) throws DaoException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            //log
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        List<Request> requestList = new ArrayList<>();
        try{
            statement = connection.prepareStatement(SELECT_REQUEST_BY_APARTMENT);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Request request = new Request();
                request.setId(resultSet.getLong(RequestTable.ID_REQUEST.getValue()));
                request.setApplicant(resultSet.getObject(RequestTable.ID_APPLICANT.getValue(), User.class));
                request.setApartment(resultSet.getObject(RequestTable.ID_APARTMENT.getValue(), Apartment.class));
                long expectedMillis = resultSet.getLong(RequestTable.EXPECTED_DATE.getValue());
                LocalDate expectedDate =
                        Instant.ofEpochMilli(expectedMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                request.setExpectedDate(expectedDate);
                long creationMillis = resultSet.getLong(RequestTable.CREATION_DATE.getValue());
                LocalDateTime creationDate =
                        Instant.ofEpochMilli(creationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                request.setCreationDate(creationDate);
                request.setDescription(resultSet.getString(RequestTable.DESCRIPTION.getValue()));
                long statusId = resultSet.getLong(RequestTable.ID_STATUS_REQUEST.getValue());
                request.setStatus(RequestStatus.getByValue(statusId));
                requestList.add(request);
            }
        } catch (SQLException | IncorrectStatusException e){
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return requestList;
    }

    @Override
    public Request update(Request request) throws DaoException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            //log
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_REQUEST);
            Instant expectedInstant = request.getExpectedDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
            long expectedMillis = expectedInstant.toEpochMilli();
            statement.setLong(1, expectedMillis);
            statement.setString(2, request.getDescription());
            statement.setLong(3, request.getStatus().getValue());
            statement.setLong(4, request.getId());
            statement.executeUpdate();
        }catch (SQLException e){
                throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return request;
    }
}
