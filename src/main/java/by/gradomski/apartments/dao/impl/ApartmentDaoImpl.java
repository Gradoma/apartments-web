package by.gradomski.apartments.dao.impl;

import by.gradomski.apartments.dao.ApartmentDao;
import by.gradomski.apartments.dao.column.ApartmentTable;
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
import java.util.Base64;
import java.util.List;
import java.util.Optional;

public class ApartmentDaoImpl implements ApartmentDao {
    private static final Logger log = LogManager.getLogger();
    private static ApartmentDaoImpl instance;
    private static final String INSERT_NEW_APARTMENT = "INSERT INTO apartment (region, city, address, rooms, square, floor, " +
            "age, furniture, description, idStatus, registrationDate, idOwner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_APARTMENTS = "SELECT idApartment, region, city, address, rooms, square, floor," +
            "age, furniture, description, idStatus, apartment.registrationDate, apartment.visibility " +
            "FROM apartment";
    private static final String SELECT_APARTMENT_BY_ID_JOIN_OWNER = "SELECT region, city, address, rooms, square, floor," +
            "age, furniture, description, idOwner, idStatus, apartment.registrationDate, apartment.visibility, " +
            "idUser, idRole, login, firstName, lastName, birthday, gender, phone, photo, user.registrationDate, mailAddress, " +
            "user.visibility FROM apartment LEFT JOIN user on idOwner=idUser WHERE idApartment=?";
    private static final String SELECT_STATUS_BY_APARTMENT_ID = "SELECT idStatus FROM apartment WHERE idApartment=?";
    private static final String SELECT_APARTMENT_BY_OWNER_ID = "SELECT idApartment, region, city, address, rooms, square, floor," +
                        "age, furniture, description, idTenant, idStatus, apartment.registrationDate, apartment.visibility, idUser, idRole, login, " +
                        "firstName, lastName, birthday, gender, phone, photo, user.registrationDate, mailAddress, user.visibility " +
                        "FROM apartment LEFT JOIN user on idTenant=idUser WHERE idOwner=? AND idStatus!=4";
    private static final String SELECT_APARTMENT_BY_TENANT_ID = "SELECT idApartment, region, city, address, rooms, square, floor," +
            "age, furniture, description, idOwner, idStatus, apartment.registrationDate, apartment.visibility, idUser, idRole, login, " +
            "firstName, lastName, birthday, gender, phone, photo, user.registrationDate, mailAddress, user.visibility " +
            "FROM apartment JOIN user on idOwner=idUser WHERE idTenant=? AND idStatus!=4";
    private static final String UPDATE_APARTMENT_BY_ID = "UPDATE apartment SET region=?, city=?, address=?, rooms=?, square=?, " +
            "floor=?, age=?, furniture=?, description=? WHERE idApartment=?";
    private static final String UPDATE_STATUS_BY_APARTMENT_ID = "UPDATE apartment SET idStatus=? WHERE idApartment=?";
    private static final String UPDATE_TENANT_BY_APARTMENT_ID = "UPDATE apartment SET idTenant=? WHERE idApartment=?";

    private ApartmentDaoImpl(){}

    public static ApartmentDaoImpl getInstance(){
        if(instance == null){
            instance = new ApartmentDaoImpl();
        }
        return instance;
    }

    @Override
    public long add(Apartment apartment) throws DaoException{
        long key = -1;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_NEW_APARTMENT, Statement.RETURN_GENERATED_KEYS);
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
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                key = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return key;
    }

    @Override
    public Optional<Apartment> findApartmentByIdWithOwner(long id) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        Optional<Apartment> optionalApartment = Optional.empty();
        try {
            statement = connection.prepareStatement(SELECT_APARTMENT_BY_ID_JOIN_OWNER);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Apartment apartment = null;
            while (resultSet.next()){
                apartment = new Apartment();
                apartment.setId(id);
                apartment.setRegion(resultSet.getString(ApartmentTable.REGION));
                apartment.setCity(resultSet.getString(ApartmentTable.CITY));
                apartment.setAddress(resultSet.getString(ApartmentTable.ADDRESS));
                apartment.setRooms(resultSet.getInt(ApartmentTable.ROOMS));
                apartment.setFloor(resultSet.getInt(ApartmentTable.FLOOR));
                apartment.setSquare(resultSet.getDouble(ApartmentTable.SQUARE));
                apartment.setYear(resultSet.getString(ApartmentTable.AGE));
                apartment.setFurniture(resultSet.getBoolean(ApartmentTable.FURNITURE));
                apartment.setDescription(resultSet.getString(ApartmentTable.DESCRIPTION));
                long apartmentStatus = resultSet.getLong(ApartmentTable.ID_STATUS);
                apartment.setStatus(ApartmentStatus.getByValue(apartmentStatus));
                long registrationMillis = resultSet.getLong(ApartmentTable.REGISTRATION_DATE);
                LocalDateTime registrationDate = Instant.ofEpochMilli(registrationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                apartment.setRegistrationDate(registrationDate);
                apartment.setVisibility(resultSet.getBoolean(ApartmentTable.VISIBILITY));

                long ownerId = resultSet.getLong(UserTable.ID_USER);
                if(ownerId != 0){
                    User owner = new User();
                    owner.setId(ownerId);
                    owner.setRole(Role.getRoleByValue(resultSet.getInt(UserTable.ID_ROLE)));
                    owner.setLoginName(resultSet.getString(UserTable.LOGIN));
                    owner.setFirstName(resultSet.getString(UserTable.FIRST_NAME));
                    owner.setLastName(resultSet.getString(UserTable.LAST_NAME));
                    long birthdayMillis = resultSet.getLong(UserTable.BIRTHDAY);
                    if(birthdayMillis != 0){
                        LocalDate birthday =
                                Instant.ofEpochMilli(birthdayMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                        owner.setBirthday(birthday);
                    }
                    String gender = resultSet.getString(UserTable.GENDER);
                    if(gender != null){
                        owner.setGender(Gender.valueOf(gender));
                    }
                    owner.setPhone(resultSet.getString(UserTable.PHONE));
                    long tenantRegisterMillis = resultSet.getLong(UserTable.REGISTRATION_DATE);
                    LocalDateTime tenantRegistrationDate = Instant.ofEpochMilli(tenantRegisterMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                    owner.setRegistrationDate(tenantRegistrationDate);
                    owner.setMail(resultSet.getString(UserTable.MAIL_ADDRESS));
                    owner.setVisibility(resultSet.getBoolean(UserTable.VISIBILITY));
                    byte[] photoBytes = resultSet.getBytes(UserTable.PHOTO);
                    String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
                    owner.setPhotoBase64(photoBase64);
                    apartment.setOwner(owner);
                }
            }
            if(apartment != null){
                optionalApartment = Optional.of(apartment);
            }
        } catch (SQLException | IncorrectStatusException | IncorrectRoleException e){
            e.printStackTrace();
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return optionalApartment;
    }

    @Override
    public List<Apartment> findApartmentsByOwner(long id) throws DaoException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        List<Apartment> apartmentList = new ArrayList<>();
        try{
            statement = connection.prepareStatement(SELECT_APARTMENT_BY_OWNER_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Apartment apartment = new Apartment();
                apartment.setId(resultSet.getLong(ApartmentTable.ID_APARTMENT));
                apartment.setRegion(resultSet.getString(ApartmentTable.REGION));
                apartment.setCity(resultSet.getString(ApartmentTable.CITY));
                apartment.setAddress(resultSet.getString(ApartmentTable.ADDRESS));
                apartment.setRooms(resultSet.getInt(ApartmentTable.ROOMS));
                apartment.setFloor(resultSet.getInt(ApartmentTable.FLOOR));
                apartment.setSquare(resultSet.getDouble(ApartmentTable.SQUARE));
                apartment.setYear(resultSet.getString(ApartmentTable.AGE));
                apartment.setFurniture(resultSet.getBoolean(ApartmentTable.FURNITURE));
                apartment.setDescription(resultSet.getString(ApartmentTable.DESCRIPTION));
                long apartmentStatus = resultSet.getLong(ApartmentTable.ID_STATUS);
                apartment.setStatus(ApartmentStatus.getByValue(apartmentStatus));
                long registrationMillis = resultSet.getLong(ApartmentTable.REGISTRATION_DATE);
                LocalDateTime registrationDate = Instant.ofEpochMilli(registrationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                apartment.setRegistrationDate(registrationDate);
                apartment.setVisibility(resultSet.getBoolean(ApartmentTable.VISIBILITY));

                long tenantId = resultSet.getLong(UserTable.ID_USER);
                if(tenantId != 0){
                    User tenant = new User();
                    tenant.setId(tenantId);
                    tenant.setRole(Role.getRoleByValue(resultSet.getInt(UserTable.ID_ROLE)));
                    tenant.setLoginName(resultSet.getString(UserTable.LOGIN));
                    tenant.setFirstName(resultSet.getString(UserTable.FIRST_NAME));
                    tenant.setLastName(resultSet.getString(UserTable.LAST_NAME));
                    long birthdayMillis = resultSet.getLong(UserTable.BIRTHDAY);
                    if(birthdayMillis != 0){
                        LocalDate birthday =
                                Instant.ofEpochMilli(birthdayMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                        tenant.setBirthday(birthday);
                    }
                    String gender = resultSet.getString(UserTable.GENDER);
                    if(gender != null){
                        tenant.setGender(Gender.valueOf(gender));
                    }
                    tenant.setPhone(resultSet.getString(UserTable.PHONE));
                    long tenantRegisterMillis = resultSet.getLong(UserTable.REGISTRATION_DATE);
                    LocalDateTime tenantRegistrationDate = Instant.ofEpochMilli(tenantRegisterMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                    tenant.setRegistrationDate(tenantRegistrationDate);
                    tenant.setMail(resultSet.getString(UserTable.MAIL_ADDRESS));
                    tenant.setVisibility(resultSet.getBoolean(UserTable.VISIBILITY));
                    byte[] photoBytes = resultSet.getBytes(UserTable.PHOTO);
                    String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
                    tenant.setPhotoBase64(photoBase64);
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
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        List<Apartment> apartmentList = new ArrayList<>();
        try{
            statement = connection.prepareStatement(SELECT_APARTMENT_BY_TENANT_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Apartment apartment = new Apartment();
                apartment.setId(resultSet.getLong(ApartmentTable.ID_APARTMENT));
                apartment.setRegion(resultSet.getString(ApartmentTable.REGION));
                apartment.setCity(resultSet.getString(ApartmentTable.CITY));
                apartment.setAddress(resultSet.getString(ApartmentTable.ADDRESS));
                apartment.setRooms(resultSet.getInt(ApartmentTable.ROOMS));
                apartment.setFloor(resultSet.getInt(ApartmentTable.FLOOR));
                apartment.setSquare(resultSet.getDouble(ApartmentTable.SQUARE));
                apartment.setYear(resultSet.getString(ApartmentTable.AGE));
                apartment.setFurniture(resultSet.getBoolean(ApartmentTable.FURNITURE));
                apartment.setDescription(resultSet.getString(ApartmentTable.DESCRIPTION));
                long apartmentStatus = resultSet.getLong(ApartmentTable.ID_STATUS);
                apartment.setStatus(ApartmentStatus.getByValue(apartmentStatus));
                long registrationMillis = resultSet.getLong(ApartmentTable.REGISTRATION_DATE);
                LocalDateTime registrationDate = Instant.ofEpochMilli(registrationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                apartment.setRegistrationDate(registrationDate);
                apartment.setVisibility(resultSet.getBoolean(ApartmentTable.VISIBILITY));

                long ownerId = resultSet.getLong(UserTable.ID_USER);
                if(ownerId != 0){
                    User owner = new User();
                    owner.setId(ownerId);
                    owner.setRole(Role.getRoleByValue(resultSet.getInt(UserTable.ID_ROLE)));
                    owner.setLoginName(resultSet.getString(UserTable.LOGIN));
                    owner.setFirstName(resultSet.getString(UserTable.FIRST_NAME));
                    owner.setLastName(resultSet.getString(UserTable.LAST_NAME));
                    long birthdayMillis = resultSet.getLong(UserTable.BIRTHDAY);
                    if(birthdayMillis != 0){
                        LocalDate birthday =
                                Instant.ofEpochMilli(birthdayMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                        owner.setBirthday(birthday);
                    }
                    String gender = resultSet.getString(UserTable.GENDER);
                    if(gender != null){
                        owner.setGender(Gender.valueOf(gender));
                    }
                    owner.setPhone(resultSet.getString(UserTable.PHONE));
                    long tenantRegisterMillis = resultSet.getLong(UserTable.REGISTRATION_DATE);
                    LocalDateTime tenantRegistrationDate = Instant.ofEpochMilli(tenantRegisterMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                    owner.setRegistrationDate(tenantRegistrationDate);
                    owner.setMail(resultSet.getString(UserTable.MAIL_ADDRESS));
                    owner.setVisibility(resultSet.getBoolean(UserTable.VISIBILITY));
                    byte[] photoBytes = resultSet.getBytes(UserTable.PHOTO);
                    String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
                    owner.setPhotoBase64(photoBase64);
                    apartment.setTenant(owner);
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
    public List<Apartment> findAll() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        Statement statement = null;
        List<Apartment> apartmentList = new ArrayList<>();
        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_APARTMENTS);
            while (resultSet.next()){
                Apartment apartment = new Apartment();
                apartment.setId(resultSet.getLong(ApartmentTable.ID_APARTMENT));
                apartment.setRegion(resultSet.getString(ApartmentTable.REGION));
                apartment.setCity(resultSet.getString(ApartmentTable.CITY));
                apartment.setAddress(resultSet.getString(ApartmentTable.ADDRESS));
                apartment.setRooms(resultSet.getInt(ApartmentTable.ROOMS));
                apartment.setFloor(resultSet.getInt(ApartmentTable.FLOOR));
                apartment.setSquare(resultSet.getDouble(ApartmentTable.SQUARE));
                apartment.setYear(resultSet.getString(ApartmentTable.AGE));
                apartment.setFurniture(resultSet.getBoolean(ApartmentTable.FURNITURE));
                apartment.setDescription(resultSet.getString(ApartmentTable.DESCRIPTION));
                long apartmentStatus = resultSet.getLong(ApartmentTable.ID_STATUS);
                apartment.setStatus(ApartmentStatus.getByValue(apartmentStatus));
                long registrationMillis = resultSet.getLong(ApartmentTable.REGISTRATION_DATE);
                LocalDateTime registrationDate = Instant.ofEpochMilli(registrationMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                apartment.setRegistrationDate(registrationDate);
                apartment.setVisibility(resultSet.getBoolean(ApartmentTable.VISIBILITY));
                apartmentList.add(apartment);
            }
        } catch (SQLException | IncorrectStatusException e){
            e.printStackTrace();
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        log.debug("apartemtnList size=" + apartmentList.size());
        return apartmentList;
    }

    @Override
    public void update(Apartment apartment) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_APARTMENT_BY_ID);
            statement.setString(1, apartment.getRegion());
            statement.setString(2, apartment.getCity());
            statement.setString(3, apartment.getAddress());
            statement.setInt(4, apartment.getRooms());
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
            statement.setLong(10, apartment.getId());
            statement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }// TODO(what return??)
    }

    @Override
    public ApartmentStatus findStatusByApartmentId(long id) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        ApartmentStatus status = null;
        try{
            statement = connection.prepareStatement(SELECT_STATUS_BY_APARTMENT_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                long statusId = resultSet.getLong(ApartmentTable.ID_STATUS);
                status = ApartmentStatus.getByValue(statusId);
            }
        } catch (SQLException | IncorrectStatusException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return status;
    }

    @Override
    public boolean updateStatusByApartmentId(long id, ApartmentStatus status) throws DaoException {
        boolean flag = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_STATUS_BY_APARTMENT_ID);
            statement.setInt(1, status.getValue());
            statement.setLong(2, id);
            int rows = statement.executeUpdate();
            if(rows == 1){
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
    public boolean updateTenantByApartmentId(long id, long tenantId) throws DaoException {
        boolean flag = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_TENANT_BY_APARTMENT_ID);
            if(tenantId > 0){
                statement.setLong(1, tenantId);
            } else {
                statement.setNull(1, Types.BIGINT);
            }
            statement.setLong(2, id);
            int rows = statement.executeUpdate();
            if(rows == 1){
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
}
