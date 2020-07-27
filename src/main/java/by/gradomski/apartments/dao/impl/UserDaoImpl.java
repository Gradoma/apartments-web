package by.gradomski.apartments.dao.impl;

import by.gradomski.apartments.dao.UserDao;
import by.gradomski.apartments.dao.column.UserTable;
import by.gradomski.apartments.entity.Gender;
import by.gradomski.apartments.entity.Role;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.exception.IncorrectRoleException;
import by.gradomski.apartments.pool.ConnectionPool;
import by.gradomski.apartments.util.PasswordEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class UserDaoImpl implements UserDao {
    private static final Logger log = LogManager.getLogger();
    private static final String INSERT_NEW_USER = "INSERT INTO user (idRole, login, password, photo, registrationDate, mailAddress) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_USERS = "SELECT idUser, idRole, login, password, firstName, lastName, birthday," +
            " gender, phone, photo, registrationDate, mailAddress, visibility FROM user";
    private static final String SELECT_USER_BY_ID = "SELECT idUser, idRole, login, password, firstName, lastName, birthday, gender, phone," +
            " photo, registrationDate, mailAddress, visibility FROM user WHERE idUser=?";
    private static final String SELECT_USER_BY_LOGIN = "SELECT idUser, idRole, login, password, firstName, lastName, birthday, gender, phone," +
            " photo, registrationDate, mailAddress, visibility FROM user WHERE login=?";
    private static final String UPDATE_USER_BY_LOGIN = "UPDATE user SET firstName=?, lastName=?, birthday=?, gender=?, phone=? WHERE login=?";
    private static final String UPDATE_PHOTO_BY_LOGIN = "UPDATE user SET photo=? WHERE login=?";
    private static final String UPDATE_USER_VISIBILITY_BY_LOGIN = "UPDATE user SET visibility=? WHERE login=?";
    private static final String DEFAULT_USER_PHOTO_PATH = "F:\\My Projects\\epam java training\\Appartment project\\testPhotos\\def_user.jpg";
    private static final String DEFAULT_ADMIN_PHOTO_PATH = "F:\\My Projects\\epam java training\\Appartment project\\testPhotos\\admin.png";
    private static UserDaoImpl instance;

    private UserDaoImpl(){}

    public static UserDaoImpl getInstance(){
        if(instance == null){
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(User user) throws DaoException{
        boolean flag = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        InputStream fileInputStream = null;
        try {
            statement = connection.prepareStatement(INSERT_NEW_USER);
            statement.setInt(1, user.getRole().getValue());
            statement.setString(2, user.getLoginName());
            String encodedPass = PasswordEncoder.encode(user.getPassword());
            statement.setString(3, encodedPass);
            File file;
            if(user.getRole() == Role.USER){
                file = new File(DEFAULT_USER_PHOTO_PATH);
            } else {
                file = new File(DEFAULT_ADMIN_PHOTO_PATH);
            }
            fileInputStream = new FileInputStream(file);
            statement.setBinaryStream(4, fileInputStream, (int) file.length());
            Instant instant = user.getRegistrationDate().atZone(ZoneId.systemDefault()).toInstant();
            long registrationMillis = instant.toEpochMilli();
            statement.setLong(5, registrationMillis);
            statement.setString(6, user.getMail());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e){
            throw new DaoException(e);
        } catch (FileNotFoundException e){
            throw new DaoException("user photo problem: ", e);
        } finally {
            close(fileInputStream);
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return flag;
    }

    @Override
    public List<User> findAll() throws DaoException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        List<User> userList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SELECT_ALL_USERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong(UserTable.ID_USER));
                user.setRole(Role.getRoleByValue(resultSet.getInt(UserTable.ID_ROLE)));
                user.setLoginName(resultSet.getString(UserTable.LOGIN));
                String decodedPass = resultSet.getString(UserTable.PASSWORD);
                user.setPassword(PasswordEncoder.decode(decodedPass));
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
                user.setVisibility(resultSet.getBoolean(UserTable.VISIBILITY));
                byte[] photoBytes = resultSet.getBytes(UserTable.PHOTO);
                String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
                user.setPhotoBase64(photoBase64);
                userList.add(user);
            }
        }catch (SQLException | IncorrectRoleException e){
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return userList;
    }

    @Override
    public Optional<User> findById(long id) throws DaoException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        Optional<User> optionalUser = Optional.empty();
        try{
            statement = connection.prepareStatement(SELECT_USER_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong(UserTable.ID_USER));
                user.setRole(Role.getRoleByValue(resultSet.getInt(UserTable.ID_ROLE)));
                user.setLoginName(resultSet.getString(UserTable.LOGIN));
                String decodedPass = resultSet.getString(UserTable.PASSWORD);
                user.setPassword(PasswordEncoder.decode(decodedPass));
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
                user.setVisibility(resultSet.getBoolean(UserTable.VISIBILITY));
                byte[] photoBytes = resultSet.getBytes(UserTable.PHOTO);
                String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
                user.setPhotoBase64(photoBase64);
            }
            if(user != null) optionalUser = Optional.of(user);
        } catch (SQLException | IncorrectRoleException e){
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        Optional<User> optionalUser = Optional.empty();
        try{
            statement = connection.prepareStatement(SELECT_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong(UserTable.ID_USER));
                user.setRole(Role.getRoleByValue(resultSet.getInt(UserTable.ID_ROLE)));
                user.setLoginName(resultSet.getString(UserTable.LOGIN));
                String decodedPass = resultSet.getString(UserTable.PASSWORD);
                user.setPassword(PasswordEncoder.decode(decodedPass));
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
                user.setVisibility(resultSet.getBoolean(UserTable.VISIBILITY));
                byte[] photoBytes = resultSet.getBytes(UserTable.PHOTO);
                log.info("photoBytes:"  + Arrays.toString(photoBytes));
                String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
                log.info("photo string: " + photoBase64);
                user.setPhotoBase64(photoBase64);
            }
            if(user != null) optionalUser = Optional.of(user);
        } catch (SQLException | IncorrectRoleException e){
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return optionalUser;
    }

    @Override
    public boolean update(User user) throws DaoException{
        boolean flag = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_USER_BY_LOGIN);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            if (user.getBirthday() != null){
                Instant instant = user.getBirthday().atStartOfDay(ZoneId.systemDefault()).toInstant();
                long birthdayMillis = instant.toEpochMilli();
                log.debug("bDay in long: " + birthdayMillis);
                statement.setLong(3, birthdayMillis);
            } else {
                statement.setNull(3, Types.BIGINT);
            }
            if (user.getGender() != null){
                statement.setString(4, user.getGender().toString());
            }
            if (user.getPhone() != null){
                statement.setString(5, user.getPhone());
            } else {
                statement.setNull(5, Types.VARCHAR);
            }
            statement.setString(6, user.getLoginName());
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
    public boolean updatePhoto(InputStream inputStream, String login) throws DaoException {
        boolean flag = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_PHOTO_BY_LOGIN);
            statement.setBinaryStream(1, inputStream, inputStream.available());
            statement.setString(2, login);
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
    public boolean deleteById(long id) throws DaoException{
        boolean flag = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_USER_VISIBILITY_BY_LOGIN);
            statement.setBoolean(1, false);
            statement.setLong(2, id);
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
    public boolean changeVisibilityByLogin(String login) throws DaoException{
        boolean flag = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        if(connection == null){
            throw new DaoException("connection is null");
        }
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_USER_VISIBILITY_BY_LOGIN);
            statement.setBoolean(1, true);
            statement.setString(2, login);
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
