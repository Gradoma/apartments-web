package by.gradomski.apartments.service.impl;

import by.gradomski.apartments.dao.impl.UserDaoImpl;
import by.gradomski.apartments.entity.Gender;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.UserService;
import by.gradomski.apartments.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
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
        Map<String, String> resultMap = new HashMap<>();
        String trueValue = "true";
        String falseValue = "false";
        resultMap.put("login", trueValue);
        resultMap.put("loginUniq", trueValue);
        resultMap.put("password", trueValue);
        resultMap.put("email", trueValue);
        resultMap = Validator.isValid(login, password, email, new HashMap<>());
        if(resultMap.containsValue(falseValue)){
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
            resultMap.put("loginUniq", falseValue);
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
    public boolean signIn(String login, String password) throws ServiceException {
        if(!Validator.isValid(login, password)){
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
    public User updateUser(String login, String password, Gender gender, String firstName,
                           String lastName, String phone, String birthday) throws ServiceException {
        User user = new User(login, password, null);
        user.setGender(gender);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        log.debug("birthday String: " + birthday);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(birthday != null){
            Date bDay;
            try {
                bDay = format.parse(birthday);
                log.debug("birthday Date: " + bDay);
            } catch (ParseException e){
                throw new ServiceException(e);
            }
            user.setBirthday(bDay);
        }
        User updatedUser = null;
        try {
            updatedUser = UserDaoImpl.getInstance().update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return updatedUser;
    }

    @Override
    public User updateUserPhoto(InputStream inputStream, String login) throws ServiceException {
        if(inputStream == null){
            throw new ServiceException("input stream is null");
        }
        Optional<User> optionalUser;
        try {
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
