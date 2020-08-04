package by.gradomski.apartments.command;

import by.gradomski.apartments.controller.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface for using service layer and preparing result for user.
 */
public interface Command {
    /** Get data from request, invoke method from service layer, and prepare result for user (next page as usual).
     * <p>
     * Should handle any Exception from the lower lever and return user error page.
     * <p>
     *
     * @param request HttpRequest from page
     * @return {@link Router} contains types of transition (forward or redirect) and next page.
     */
    Router execute(HttpServletRequest request);
}
