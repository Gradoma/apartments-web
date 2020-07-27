package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static by.gradomski.apartments.command.PagePath.ADMIN_USERS;
import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;

public class AdminToUserListCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String USER_LIST = "userList";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        try{
            List<User> allUsers = UserServiceImpl.getInstance().getAll();
            request.setAttribute(USER_LIST, allUsers);
            page = ADMIN_USERS;
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(ADMIN_USERS);
        return router;
    }
}
