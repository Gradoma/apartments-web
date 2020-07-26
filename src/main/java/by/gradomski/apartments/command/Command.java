package by.gradomski.apartments.command;

import by.gradomski.apartments.controller.Router;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request);
}
