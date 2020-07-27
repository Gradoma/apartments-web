package by.gradomski.apartments.service.impl;

import by.gradomski.apartments.dao.impl.UserDaoImpl;
import by.gradomski.apartments.entity.Gender;
import by.gradomski.apartments.entity.Role;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.UserService;
import by.gradomski.apartments.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static final String FALSE = "false";
    private static final Logger log = LogManager.getLogger();

    private UserServiceImpl(){}

    public static UserServiceImpl getInstance(){
        if(instance == null){
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public Map<String, String> signUp(String login, String password, String email) throws ServiceException {
        Map<String, String> resultMap;
        resultMap = UserValidator.isValid(login, password, email);
        if(resultMap.containsValue(FALSE)){
            return resultMap;
        }
        Optional<User> optionalUser;
        try{
            optionalUser = UserDaoImpl.getInstance().findByLogin(login);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        if(optionalUser.isPresent()){
            log.debug("user with this login already exist");
            resultMap.put("loginUniq", FALSE);
            return resultMap;
        }
        User user = new User(login, password, email);
        try {
            UserDaoImpl.getInstance().add(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return resultMap;
    }

    @Override
    public Map<String, String> createNewAdmin(String login, String password, String email) throws ServiceException {
        Map<String, String> resultMap;
        resultMap = UserValidator.isValid(login, password, email);
        if(resultMap.containsValue(FALSE)){
            return resultMap;
        }
        Optional<User> optionalUser;
        try{
            optionalUser = UserDaoImpl.getInstance().findByLogin(login);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        if(optionalUser.isPresent()){
            log.debug("user with this login already exist");
            resultMap.put("loginUniq", FALSE);
            return resultMap;
        }
        User user = new User(login, password, email);
        user.setRole(Role.ADMIN);
        try {
            UserDaoImpl.getInstance().add(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return resultMap;
    }

    @Override
    public boolean signIn(String login, String password) throws ServiceException {
        if(!UserValidator.isValid(login, password)){
            return false;
        }
        Optional<User> optionalUser;
        try{
            optionalUser = UserDaoImpl.getInstance().findByLogin(login);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        if(optionalUser.isEmpty()){
            log.debug("user wasn't found: " + login);
            return false;
        }
        User user = optionalUser.get();
        if(!user.isVisible()){
            log.debug(login + " - email doesn't confirm or user has been deleted");
            return false;
        }
        if (password.equals(user.getPassword())){
            return true;
        }
        log.debug("incorrect password");
        return false;
    }

    @Override
    public void activateUser(String login) throws ServiceException {
        try {
            UserDaoImpl.getInstance().changeVisibilityByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAll() throws ServiceException {
        List<User> allUsers;
        try {
            allUsers = UserDaoImpl.getInstance().findAll();
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return allUsers;
    }

    @Override
    public User getUserByLogin(String login) throws ServiceException {
        Optional<User> optionalUser;
        try{
            optionalUser = UserDaoImpl.getInstance().findByLogin(login);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        if(optionalUser.isEmpty()){
            log.error("user wasn't found: " + login);
            throw new ServiceException("user wasn't found: " + login);
        }
        return optionalUser.get();
    }

    @Override
    public User getUserById(long id) throws ServiceException {
        Optional<User> optionalUser;
        try{
            optionalUser = UserDaoImpl.getInstance().findById(id);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        if(optionalUser.isEmpty()){
            throw new ServiceException("user wasn't found, id: " + id);
        }
        return optionalUser.get();
    }

    @Override
    public User updateUser(String login, String genderString, String firstName,
                           String lastName, String phone, String birthdayString) throws ServiceException {
        User user = new User();
        user.setLoginName(login);
        Gender gender = Gender.valueOf(genderString);
        user.setGender(gender);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        try {
            LocalDate birthday = LocalDate.parse(birthdayString);
            user.setBirthday(birthday);
        } catch (DateTimeParseException pEx){
            throw new ServiceException(pEx);
        }
        Optional<User> optionalUser = Optional.empty();
        try {
            boolean updateResult = UserDaoImpl.getInstance().update(user);
            if(updateResult){
                optionalUser = UserDaoImpl.getInstance().findByLogin(login);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if(optionalUser.isEmpty()){
            throw new ServiceException("user wasn't found, login: " + login);
        }
        return optionalUser.get();
    }

    @Override
    public User updateUserPhoto(InputStream inputStream, String login) throws ServiceException {
        if(inputStream == null){
            throw new ServiceException("input stream is null");
        }
        Optional<User> optionalUser;
        try {
//            if(!ImageValidator.isValid(inputStream)){
//                throw new ImageValidationException();
//            }
//            log.debug("image validation: success");
            boolean updateResult = UserDaoImpl.getInstance().updatePhoto(inputStream, login);
            optionalUser = UserDaoImpl.getInstance().findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if(optionalUser.isEmpty()){
            log.error("user wasn't found: " + login);
            throw new ServiceException("user wasn't found: " + login);
        }
        return optionalUser.get();
    }
}
