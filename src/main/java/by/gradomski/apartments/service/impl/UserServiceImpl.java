package by.gradomski.apartments.service.impl;

import by.gradomski.apartments.dao.impl.UserDaoImpl;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.UserService;
import by.gradomski.apartments.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    // TODO(make singleton)
    private static final Logger log = LogManager.getLogger();

    @Override
    public boolean signUp(String login, String password, String email) throws ServiceException {
        if(!Validator.isValid(login, password, email)){
            return false;
        }
        Optional<User> optionalUser;
        try{
            optionalUser = UserDaoImpl.getInstance().findByLogin(login);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        if(optionalUser.isPresent()){
            log.debug("user with this login already exist");
            return false;
        }
        User user = new User(login, password, email);
        try {
            UserDaoImpl.getInstance().add(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
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
            log.debug("incorrect user");
            return false;
        }
        User user = optionalUser.get();
        if (password.equals(user.getPassword())){
            return true;
        }
        log.debug("incorrect password");
        return false;
    }
}
