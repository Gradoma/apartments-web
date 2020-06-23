package by.gradomski.apartments.service;

import by.gradomski.apartments.exception.ServiceException;

import java.util.Map;

public interface UserService {
    Map<String, String> signUp(String login, String password, String email) throws ServiceException;
    boolean signIn(String login, String password) throws ServiceException;
    void activateUser(String login) throws ServiceException;
}
