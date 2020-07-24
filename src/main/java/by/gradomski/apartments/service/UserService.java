package by.gradomski.apartments.service;

import by.gradomski.apartments.entity.Gender;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;

import java.io.InputStream;
import java.util.Map;

public interface UserService {
    Map<String, String> signUp(String login, String password, String email) throws ServiceException;
    boolean signIn(String login, String password) throws ServiceException;
    void activateUser(String login) throws ServiceException;
    User getUserByLogin(String login) throws ServiceException;
    User getUserById(long id) throws ServiceException;
    User updateUser(String login, String gender, String firstName,
                    String lastName, String phone, String birthday) throws ServiceException;
    User updateUserPhoto(InputStream inputStream, String login) throws ServiceException;
}
