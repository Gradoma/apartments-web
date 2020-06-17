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
    private static final Logger log = LogManager.getLogger();

    @Override
    public void signUp(String login, String password, String email) throws ServiceException {
        // TODO
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
