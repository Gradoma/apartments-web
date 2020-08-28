package by.gradomski.apartments.controller;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.command.CommandProvider;
import by.gradomski.apartments.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/control")
public class Controller extends HttpServlet {
    private static final Logger log = LogManager.getLogger(Controller.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void destroy(){
        ConnectionPool.getInstance().destroyPool();
        log.debug("pool was destroyed");
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        log.debug("start method processRequest");
        log.debug("comand parameter from request: " + request.getParameter("command"));
        Optional<Command> optionalCommand = CommandProvider.defineCommand(request.getParameter("command"));
        String page;
        if(optionalCommand.isPresent()){
            Command command = optionalCommand.get();
            log.debug("command: " + command);
            Router router = command.execute(request);
            page = router.getPage();
            log.debug("transition type: " + router.getTransitionType());
            if(Router.TransitionType.FORWARD == router.getTransitionType()){
                RequestDispatcher dispatcher = request.getRequestDispatcher(page);
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + page);
            }
        } else {
            log.error("incorrect command: " + request.getParameter("command"));
            throw new UnsupportedOperationException();
        }
    }
}
