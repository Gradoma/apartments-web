package by.gradomski.apartments.controller;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.command.CommandProvider;
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

    private void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        Optional<Command> optionalCommand = CommandProvider.defineCommand(request.getParameter("command"));
        String page;
        if(!optionalCommand.isEmpty()){
            Command command = optionalCommand.get();
            page = command.execute(request);
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            throw new UnsupportedOperationException();
        }




        log.warn("start servlet");
        String fn = request.getParameter("firstName");
        log.warn("name");
        String ln = request.getParameter("lastName");
        log.warn("last name");
        String pass = request.getParameter("password");
        log.warn("pass");
        request.setAttribute("firstName", fn);
        request.setAttribute("lastName", ln);
        request.setAttribute("password", pass);
        request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        log.warn("end servlet");
    }
}
