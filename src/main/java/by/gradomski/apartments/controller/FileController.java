package by.gradomski.apartments.controller;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.command.CommandProvider;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ImageValidationException;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.gradomski.apartments.command.PagePath.USER_SETTINGS;

@WebServlet(urlPatterns = "/fileController")
@MultipartConfig(fileSizeThreshold = 1024*1024, maxFileSize = 1024*1024*5, maxRequestSize = 1024*1024*5*5)
public class FileController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(FileController.class);
    private static final String IMAGE_TYPE_PATTERN = "(?i)(png|gif|jpg|jpeg)(?-i)$";
    private static final String INCORRECT_TYPE = "incorrectType";
    private static final String EMPTY_FILE = "emptyFile";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        InputStream inputStream = null;
        Part filePart = request.getPart("image");
        log.info("contentType" + filePart.getContentType());
        Pattern pattern = Pattern.compile(IMAGE_TYPE_PATTERN);
        Matcher matcher = pattern.matcher(filePart.getContentType());
        if(!matcher.find()){
            request.setAttribute(INCORRECT_TYPE, true);
        } else {
            if(filePart != null){
                inputStream = filePart.getInputStream();
                try {
                    User updatedUser = UserServiceImpl.getInstance().updateUserPhoto(inputStream, login);
                    request.getSession().setAttribute("user", updatedUser);
                } catch (ServiceException e) {
                    log.error("file upload failed: " + e);
                    throw new UnsupportedOperationException(e);
                }
            } else {
                request.setAttribute(EMPTY_FILE, true);
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(USER_SETTINGS);
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
