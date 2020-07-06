package by.gradomski.apartments.dao.impl;

import by.gradomski.apartments.constant.ApartmentTable;
import by.gradomski.apartments.constant.UserTable;
import by.gradomski.apartments.dao.ApartmentDao;
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

public class ApartmentDaoImpl implements ApartmentDao {
    private static final Logger log = LogManager.getLogger();
    private static ApartmentDaoImpl instance;
    private static final String INSERT_NEW_APARTMENT = "INSERT INTO apartment (region, city, address, rooms, square, floor, " +
            "age, furniture, description, idStatus, registrationDate, idOwner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_APARTMENT_BY_OWNER_ID = "SELECT idApartment, region, city, address, rooms, square, floor," +
                        "age, furniture, description, idTenant, idStatus, apartment.registrationDate, apartment.visibility, idUser, idRole, login, password, " +
                        "firstName, lastName, birthday, gender, phone, photo, user.registrationDate, mailAddress, user.visibility " +
                        "FROM apartment LEFT JOIN user on idTenant=idUser WHERE idOwner=?";//TODO(choose except DELETED status)


    private ApartmentDaoImpl(){}

    public static ApartmentDaoImpl getInstance(){
        if(instance == null){
            instance = new ApartmentDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Apartment apartment) throws DaoException{
        boolean flag = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            log.error("connection pool returned null connection");
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_NEW_APARTMENT);
            statement.setString(1, apartment.getRegion());
            statement.setString(2, apartment.getCity());
            statement.setString(3, apartment.getAddress());
            int rooms = apartment.getRooms();
            statement.setInt(4, rooms);
            double square = apartment.getSquare();
            if(Double.compare(square, 0.0) == 0){
                statement.setNull(5, Types.DOUBLE);
            } else {
                statement.setDouble(5, square);
            }
            int floor = apartment.getFloor();
            if(floor != 0){
                statement.setInt(6, floor);
            } else {
                statement.setNull(6, Types.INTEGER);
            }
            String age = apartment.getYear();
            if(age != null){
                statement.setString(7, age);
            } else {
                statement.setNull(7, Types.VARCHAR);
            }
            statement.setBoolean(8, apartment.hasFurniture());
            String description = apartment.getDescription();
            if(description != null){
                statement.setString(9, description);
            } else {
                statement.setNull(9, Types.VARCHAR);
            }
            statement.setInt(10, apartment.getStatus().getValue());
            Instant instant = apartment.getRegistrationDate().atZone(ZoneId.systemDefault()).toInstant();
            long registrationMillis = instant.toEpochMilli();
            statement.setLong(11, registrationMillis);
            log.debug("owner id: " + apartment.getOwner().getId());
            statement.setLong(12, apartment.getOwner().getId());
            int rows = statement.executeUpdate();
            if(rows == 0){
                log.warn(rows + " lines updated in DB, table apartment");
            } else {
                flag = true;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return flag;
    }


    @Override
    public List<Apartment> findApartmentsByOwner(long id) throws DaoException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            log.error("connection pool returned null connection");
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        List<Apartment> apartmentList = new ArrayList<>();
        try{
            statement = connection.prepareStatement(SELECT_APARTMENT_BY_OWNER_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            log.debug("execute query - ok");
            while (resultSet.next()){
                Apartment apartment = new Apartment();
//                apartment.setId(resultSet.getLong(ApartmentTable.ID_APARTMENT.getValue()));
//                log.debug("res set get long id - ok");
                apartment.setRegion(resultSet.getString(ApartmentTable.REGION.getValue()));
                apartment.setCity(resultSet.getString(ApartmentTable.CITY.getValue()));
                apartment.setAddress(resultSet.getString(ApartmentTable.ADDRESS.getValue()));
                apartment.setRooms(resultSet.getInt(ApartmentTable.ROOMS.getValue()));
                apartment.setFloor(resultSet.getInt(ApartmentTable.FLOOR.getValue()));
                apartment.setSquare(resultSet.getDouble(ApartmentTable.SQUARE.getValue()));
                apartment.setYear(resultSet.getString(ApartmentTable.AGE.getValue()));
                apartment.setFurniture(resultSet.getBoolean(ApartmentTable.FURNITURE.getValue()));
                apartment.setDescription(resultSet.getString(ApartmentTable.DESCRIPTION.getValue()));
                long apartmentStatus = resultSet.getLong(ApartmentTable.ID_STATUS.getValue());
                apartment.setStatus(ApartmentStatus.getByValue(apartmentStatus));
                long registrationMillis = resultSet.getLong(ApartmentTable.REGISTRATION_DATE.getValue());
                LocalDateTime registrationDate = Instant.ofEpochMilli(registrationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                apartment.setRegistrationDate(registrationDate);
                apartment.setVisibility(resultSet.getBoolean(ApartmentTable.VISIBILITY.getValue()));

                long tenantId = resultSet.getLong(UserTable.ID_USER.getValue());
                if(tenantId != 0){
                    User tenant = new User();
                    tenant.setId(tenantId);
                    tenant.setRole(Role.getRoleByValue(resultSet.getInt(UserTable.ID_ROLE.getValue())));
                    tenant.setLoginName(resultSet.getString(UserTable.LOGIN.getValue()));
                    tenant.setPassword(resultSet.getString(UserTable.PASSWORD.getValue()));
                    tenant.setFirstName(resultSet.getString(UserTable.FIRST_NAME.getValue()));
                    tenant.setLastName(resultSet.getString(UserTable.LAST_NAME.getValue()));
                    long birthdayMillis = resultSet.getLong(UserTable.BIRTHDAY.getValue());
                    if(birthdayMillis != 0){
                        LocalDate birthday =
                                Instant.ofEpochMilli(birthdayMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                        tenant.setBirthday(birthday);
                    }
                    String gender = resultSet.getString(UserTable.GENDER.getValue());
                    if(gender != null){
                        tenant.setGender(Gender.valueOf(gender));
                    }
                    tenant.setPhone(resultSet.getString(UserTable.PHONE.getValue()));
                    long tenantRegisterMillis = resultSet.getLong(UserTable.REGISTRATION_DATE.getValue());
                    LocalDateTime tenantRegistrationDate = Instant.ofEpochMilli(tenantRegisterMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                    tenant.setRegistrationDate(tenantRegistrationDate);
                    tenant.setMail(resultSet.getString(UserTable.MAIL_ADDRESS.getValue()));
                    tenant.setVisibility(resultSet.getBoolean(UserTable.VISIBILITY.getValue()));
                    log.debug("tenant: " + tenant);
                    apartment.setTenant(tenant);
                }

                apartmentList.add(apartment);
            }
        } catch (SQLException | IncorrectStatusException | IncorrectRoleException e){
            e.printStackTrace();
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return apartmentList;
    }

    @Override
    public List<Apartment> findApartmentsByTenant(long id) throws DaoException {
        return null;
    }

    @Override
    public List<Apartment> findAll() throws DaoException {
        return null;
    }

    @Override
    public Apartment update(Apartment apartment) throws DaoException {
        return null;
    }

    @Override
    public boolean deleteApartmentById(long id) throws DaoException {
        return false;
    }
}
