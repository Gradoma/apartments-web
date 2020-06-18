package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;

import javax.servlet.http.HttpServletRequest;

import static by.gradomski.apartments.command.PagePath.SIGN_UP;

public class TransitionToSignUpCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return SIGN_UP;
    }
}
