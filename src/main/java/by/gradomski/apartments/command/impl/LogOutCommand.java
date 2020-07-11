package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.gradomski.apartments.command.PagePath.SIGN_IN;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return SIGN_IN;
    }
}
