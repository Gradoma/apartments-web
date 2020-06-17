package by.gradomski.apartments.service;

import by.gradomski.apartments.exception.ServiceException;

public interface UserService {
    boolean signUp(String login, String password, String email) throws ServiceException;
    boolean signIn(String login, String password) throws ServiceException;
}
