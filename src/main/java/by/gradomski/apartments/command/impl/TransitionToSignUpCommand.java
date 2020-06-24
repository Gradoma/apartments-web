package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;

import javax.servlet.http.HttpServletRequest;

import static by.gradomski.apartments.command.PagePath.USER_SETTINGS;

public class TransitionToSignUpCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return USER_SETTINGS;
    }
}
