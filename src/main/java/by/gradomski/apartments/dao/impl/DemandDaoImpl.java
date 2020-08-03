package by.gradomski.apartments.dao.impl;

import by.gradomski.apartments.dao.DemandDao;
import by.gradomski.apartments.dao.column.ApartmentTable;
import by.gradomski.apartments.dao.column.DemandTable;
import by.gradomski.apartments.dao.column.UserTable;
import by.gradomski.apartments.entity.*;
import by.gradomski.apartments.exception.DaoException;
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
import java.util.Base64;
import java.util.List;
import java.util.Optional;

public class DemandDaoImpl implements DemandDao {
    private static final Logger log = LogManager.getLogger();
    private static final String INSERT_NEW_REQUEST = "INSERT INTO request (idApplicant, idApartment, expectedDate, description," +
            " creationDate, idStatusReq) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_REQUESTS = "SELECT idRequest, idApartment, expectedDate, creationDate," +
            " request.description, idStatusReq FROM request";
    private static final String SELECT_REQUEST_BY_ID = "SELECT idApartment, idApplicant, expectedDate, creationDate," +
            " request.description, idStatusReq FROM request WHERE idRequest=?";
    private static final String SELECT_REQUEST_BY_APPLICANT_ID = "SELECT idRequest, idApplicant, idApartment, expectedDate, creationDate," +
            " request.description, idStatusReq FROM request WHERE idApplicant=? AND idStatusReq!=5";
    private static final String SELECT_REQUEST_BY_APARTMENT_ID = "SELECT idRequest, idApplicant, idApartment, expectedDate, " +
            "creationDate, request.description, idStatusReq, idUser, firstName, lastName, birthday, gender, phone, photo, " +
            "registrationDate FROM request JOIN user ON idApplicant=idUser WHERE idApartment=? AND idStatusReq!=4;";
    private static final String UPDATE_REQUEST = "UPDATE request SET expectedDate=?, description=?, idStatusReq=? WHERE idRequest=?";
    private static final String UPDATE_STATUS_BY_ID = "UPDATE request SET idStatusReq=? WHERE idRequest=?";
    private static DemandDaoImpl instance;

    private DemandDaoImpl(){}

    public static DemandDaoImpl getInstance(){
        if(instance == null){
            instance = new DemandDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Demand demand) throws DaoException{
        boolean flag = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_NEW_REQUEST);
            statement.setLong(1, demand.getApplicant().getId());
            statement.setLong(2, demand.getApartmentId());
            Instant expectedInstant = demand.getExpectedDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
            long expectedMillis = expectedInstant.toEpochMilli();
            statement.setLong(3, expectedMillis);
            if(demand.getDescription() != null){
                statement.setString(4, demand.getDescription());
            } else {
                statement.setNull(4, Types.VARCHAR);
            }
            Instant creationInstant = demand.getCreationDate().atZone(ZoneId.systemDefault()).toInstant();
            long creationMillis = creationInstant.toEpochMilli();
            statement.setLong(5, creationMillis);
            statement.setLong(6, demand.getStatus().getValue());
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
    public List<Demand> findAll() throws DaoException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        Statement statement = null;
        List<Demand> demandList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_REQUESTS);
            while (resultSet.next()){
                Demand demand = new Demand();
                demand.setId(resultSet.getLong(DemandTable.ID_REQUEST));
                demand.setApartmentId(resultSet.getLong(ApartmentTable.ID_APARTMENT));
                long expectedMillis = resultSet.getLong(DemandTable.EXPECTED_DATE);
                LocalDate expectedDate =
                        Instant.ofEpochMilli(expectedMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                demand.setExpectedDate(expectedDate);
                long creationMillis = resultSet.getLong(DemandTable.CREATION_DATE);
                LocalDateTime creationDate =
                        Instant.ofEpochMilli(creationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                demand.setCreationDate(creationDate);
                demand.setDescription(resultSet.getString(DemandTable.DESCRIPTION));
                long statusId = resultSet.getLong(DemandTable.ID_STATUS_REQUEST);
                demand.setStatus(DemandStatus.getByValue(statusId));
                demandList.add(demand);
            }
        }catch (SQLException | IncorrectStatusException e){
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return demandList;
    }

    @Override
    public Optional<Demand> findById(long id) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        Optional<Demand> optionalRequest = Optional.empty();
        try{
            statement = connection.prepareStatement(SELECT_REQUEST_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Demand demand = null;
            while (resultSet.next()) {
                demand = new Demand();
                demand.setId(id);
                demand.setApartmentId(resultSet.getLong(DemandTable.ID_APARTMENT));
                long expectedMillis = resultSet.getLong(DemandTable.EXPECTED_DATE);
                LocalDate expectedDate =
                        Instant.ofEpochMilli(expectedMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                demand.setExpectedDate(expectedDate);
                long creationMillis = resultSet.getLong(DemandTable.CREATION_DATE);
                LocalDateTime creationDate =
                        Instant.ofEpochMilli(creationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                demand.setCreationDate(creationDate);
                demand.setDescription(resultSet.getString(DemandTable.DESCRIPTION));
                long statusId = resultSet.getLong(DemandTable.ID_STATUS_REQUEST);
                demand.setStatus(DemandStatus.getByValue(statusId));
                User applicant = new User();
                applicant.setId(resultSet.getLong(DemandTable.ID_APPLICANT));
                demand.setApplicant(applicant);
            }
            if(demand != null) optionalRequest = Optional.of(demand);
        } catch (SQLException | IncorrectStatusException e){
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return optionalRequest;
    }

    @Override
    public List<Demand> findByApplicant(long id) throws DaoException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        List<Demand> demandList = new ArrayList<>();
        try{
            statement = connection.prepareStatement(SELECT_REQUEST_BY_APPLICANT_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Demand demand = new Demand();
                demand.setId(resultSet.getLong(DemandTable.ID_REQUEST));
                demand.setApartmentId(resultSet.getLong(DemandTable.ID_APARTMENT));
                long expectedMillis = resultSet.getLong(DemandTable.EXPECTED_DATE);
                LocalDate expectedDate =
                        Instant.ofEpochMilli(expectedMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                demand.setExpectedDate(expectedDate);
                long creationMillis = resultSet.getLong(DemandTable.CREATION_DATE);
                LocalDateTime creationDate =
                        Instant.ofEpochMilli(creationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                demand.setCreationDate(creationDate);
                demand.setDescription(resultSet.getString(DemandTable.DESCRIPTION));
                long statusId = resultSet.getLong(DemandTable.ID_STATUS_REQUEST);
                demand.setStatus(DemandStatus.getByValue(statusId));
                demandList.add(demand);
            }
        } catch (SQLException | IncorrectStatusException e){
                throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return demandList;
    }

    @Override
    public List<Demand> findByApartment(long id) throws DaoException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        List<Demand> demandList = new ArrayList<>();
        try{
            statement = connection.prepareStatement(SELECT_REQUEST_BY_APARTMENT_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Demand demand = new Demand();
                demand.setId(resultSet.getLong(DemandTable.ID_REQUEST));
                demand.setApartmentId(resultSet.getLong(DemandTable.ID_APARTMENT));
                long expectedMillis = resultSet.getLong(DemandTable.EXPECTED_DATE);
                LocalDate expectedDate =
                        Instant.ofEpochMilli(expectedMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                demand.setExpectedDate(expectedDate);
                long creationMillis = resultSet.getLong(DemandTable.CREATION_DATE);
                LocalDateTime creationDate =
                        Instant.ofEpochMilli(creationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                demand.setCreationDate(creationDate);
                demand.setDescription(resultSet.getString(DemandTable.DESCRIPTION));
                long statusId = resultSet.getLong(DemandTable.ID_STATUS_REQUEST);
                demand.setStatus(DemandStatus.getByValue(statusId));

                long applicantId = resultSet.getLong(DemandTable.ID_APPLICANT);
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
                byte[] photoBytes = resultSet.getBytes(UserTable.PHOTO);
                String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
                applicant.setPhotoBase64(photoBase64);

                demand.setApplicant(applicant);
                demandList.add(demand);
            }
        } catch (SQLException | IncorrectStatusException e){
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return demandList;
    }

    @Override
    public Demand update(Demand demand) throws DaoException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_REQUEST);
            Instant expectedInstant = demand.getExpectedDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
            long expectedMillis = expectedInstant.toEpochMilli();
            statement.setLong(1, expectedMillis);
            statement.setString(2, demand.getDescription());
            statement.setLong(3, demand.getStatus().getValue());
            statement.setLong(4, demand.getId());
            statement.executeUpdate();
        }catch (SQLException e){
                throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return demand;
    }

    @Override
    public boolean updateStatusById(long id, DemandStatus status) throws DaoException {
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
