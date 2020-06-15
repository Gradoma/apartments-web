package by.gradomski.apartments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
