package by.gradomski.apartments.dao.impl;

import by.gradomski.apartments.dao.RequestDao;
import by.gradomski.apartments.dao.column.ApartmentTable;
import by.gradomski.apartments.dao.column.RequestTable;
import by.gradomski.apartments.dao.column.UserTable;
import by.gradomski.apartments.entity.*;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.exception.IncorrectRoleException;
import by.gradomski.apartments.exception.IncorrectStatusException;
import by.gradomski.apartments.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class RequestDaoImpl implements RequestDao {
    private static final Logger log = LogManager.getLogger();
    private static final String INSERT_NEW_REQUEST = "INSERT INTO request (idApplicant, idApartment, expectedDate, description," +
            " creationDate, idStatusReq) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_REQUESTS = "SELECT idRequest, idApplicant, idApartment, expectedDate, creationDate," +
            " request.description, idStatusReq, idUser, idRole, login, firstName, lastName, birthday, gender, phone," +
            "user.registrationDate, mailAddress, idAppartment, region, city, address, rooms, square, floor FROM request JOIN user ON idApplicant=idUser JOIN appartment ON idApartment=idAppartment";
    private static final String SELECT_REQUEST_BY_APPLICANT_ID = "SELECT idRequest, idApplicant, idApartment, expectedDate, creationDate," +
            " request.description, idStatusReq FROM request WHERE idApplicant=?";
    private static final String SELECT_REQUEST_BY_APARTMENT_ID = "SELECT idRequest, idApplicant, idApartment, expectedDate, " +
            "creationDate, request.description, idStatusReq, idUser, firstName, lastName, birthday, gender, phone, " +
            "registrationDate FROM request JOIN user ON idApplicant=idUser WHERE idApartment=? AND idStatusReq!=4;";
    private static final String UPDATE_REQUEST = "UPDATE request SET expectedDate=?, description=?, idStatusReq=? WHERE idRequest=?";
    private static final String UPDATE_STATUS_BY_ID = "UPDATE request SET idStatusReq=? WHERE idRequest=?";
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
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_NEW_REQUEST);
            statement.setLong(1, request.getApplicant().getId());
            statement.setLong(2, request.getApartmentId());
            Instant expectedInstant = request.getExpectedDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
            long expectedMillis = expectedInstant.toEpochMilli();
            statement.setLong(3, expectedMillis);
            if(request.getDescription() != null){
                statement.setString(4, request.getDescription());
            } else {
                statement.setNull(4, Types.VARCHAR);
            }
            Instant creationInstant = request.getCreationDate().atZone(ZoneId.systemDefault()).toInstant();
            long creationMillis = expectedInstant.toEpochMilli();
            statement.setLong(5, creationMillis);
            statement.setLong(6, request.getStatus().getValue());
            int rows = statement.executeUpdate();
            if(rows != 0){
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
    public List<Request> findAll() throws DaoException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        List<Request> requestList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SELECT_ALL_REQUESTS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong(RequestTable.ID_APPLICANT));
                user.setRole(Role.getRoleByValue(resultSet.getInt(UserTable.ID_ROLE)));
                user.setLoginName(resultSet.getString(UserTable.LOGIN));
                user.setPassword(resultSet.getString(UserTable.PASSWORD));
                user.setFirstName(resultSet.getString(UserTable.FIRST_NAME));
                user.setLastName(resultSet.getString(UserTable.LAST_NAME));
                long birthdayMillis = resultSet.getLong(UserTable.BIRTHDAY);
                if(birthdayMillis != 0){
                    LocalDate birthday =
                            Instant.ofEpochMilli(birthdayMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                    user.setBirthday(birthday);
                }
                String gender = resultSet.getString(UserTable.GENDER);
                if(gender != null){
                    user.setGender(Gender.valueOf(gender));
                }
                user.setPhone(resultSet.getString(UserTable.PHONE));
                long registrationMillis = resultSet.getLong(UserTable.REGISTRATION_DATE);
                LocalDateTime registrationDate = Instant.ofEpochMilli(registrationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                user.setRegistrationDate(registrationDate);
                user.setMail(resultSet.getString(UserTable.MAIL_ADDRESS));

                Request request = new Request();
                request.setId(resultSet.getLong(RequestTable.ID_REQUEST));
                request.setApplicant(user);
                request.setApartmentId(resultSet.getLong(ApartmentTable.ID_APARTMENT));
                long expectedMillis = resultSet.getLong(RequestTable.EXPECTED_DATE);
                LocalDate expectedDate =
                        Instant.ofEpochMilli(expectedMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                request.setExpectedDate(expectedDate);
                long creationMillis = resultSet.getLong(RequestTable.CREATION_DATE);
                LocalDateTime creationDate =
                        Instant.ofEpochMilli(creationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                request.setCreationDate(creationDate);
                request.setDescription(resultSet.getString(RequestTable.DESCRIPTION));
                long statusId = resultSet.getLong(RequestTable.ID_STATUS_REQUEST);
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
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        List<Request> requestList = new ArrayList<>();
        try{
            statement = connection.prepareStatement(SELECT_REQUEST_BY_APPLICANT_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Request request = new Request();
                request.setId(resultSet.getLong(RequestTable.ID_REQUEST));
                request.setApartmentId(resultSet.getLong(RequestTable.ID_APARTMENT));
                long expectedMillis = resultSet.getLong(RequestTable.EXPECTED_DATE);
                LocalDate expectedDate =
                        Instant.ofEpochMilli(expectedMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                request.setExpectedDate(expectedDate);
                long creationMillis = resultSet.getLong(RequestTable.CREATION_DATE);
                LocalDateTime creationDate =
                        Instant.ofEpochMilli(creationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                request.setCreationDate(creationDate);
                request.setDescription(resultSet.getString(RequestTable.DESCRIPTION));
                long statusId = resultSet.getLong(RequestTable.ID_STATUS_REQUEST);
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
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        List<Request> requestList = new ArrayList<>();
        try{
            statement = connection.prepareStatement(SELECT_REQUEST_BY_APARTMENT_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Request request = new Request();
                request.setId(resultSet.getLong(RequestTable.ID_REQUEST));
                request.setApartmentId(resultSet.getLong(RequestTable.ID_APARTMENT));
                long expectedMillis = resultSet.getLong(RequestTable.EXPECTED_DATE);
                LocalDate expectedDate =
                        Instant.ofEpochMilli(expectedMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                request.setExpectedDate(expectedDate);
                long creationMillis = resultSet.getLong(RequestTable.CREATION_DATE);
                LocalDateTime creationDate =
                        Instant.ofEpochMilli(creationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                request.setCreationDate(creationDate);
                request.setDescription(resultSet.getString(RequestTable.DESCRIPTION));
                long statusId = resultSet.getLong(RequestTable.ID_STATUS_REQUEST);
                request.setStatus(RequestStatus.getByValue(statusId));

                long applicantId = resultSet.getLong(RequestTable.ID_APPLICANT);
                User applicant = new User();
                applicant.setId(resultSet.getLong(UserTable.ID_USER));
                applicant.setFirstName(resultSet.getString(UserTable.FIRST_NAME));
                applicant.setLastName(resultSet.getString(UserTable.LAST_NAME));
                long birthdayMillis = resultSet.getLong(UserTable.BIRTHDAY);
                if(birthdayMillis != 0){
                    LocalDate birthday =
                            Instant.ofEpochMilli(birthdayMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                    applicant.setBirthday(birthday);
                }
                String gender = resultSet.getString(UserTable.GENDER);
                if(gender != null){
                    applicant.setGender(Gender.valueOf(gender));
                }
                applicant.setPhone(resultSet.getString(UserTable.PHONE));
                long applicantRegisterMillis = resultSet.getLong(UserTable.REGISTRATION_DATE);
                LocalDateTime tenantRegistrationDate = Instant.ofEpochMilli(applicantRegisterMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                applicant.setRegistrationDate(tenantRegistrationDate);

                request.setApplicant(applicant);
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

    @Override
    public boolean updateStatusById(long id, RequestStatus status) throws DaoException {
        boolean flag = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_STATUS_BY_ID);
            statement.setLong(1, status.getValue());
            statement.setLong(2, id);
            int rows = statement.executeUpdate();
            if(rows != 0){
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
}
