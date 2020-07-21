package by.gradomski.apartments.controller;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.command.CommandProvider;
import by.gradomski.apartments.command.PagePath;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ImageValidationException;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.PhotoApartmentServiceImpl;
import by.gradomski.apartments.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.gradomski.apartments.command.PagePath.*;

@WebServlet(urlPatterns = "/fileController")
@MultipartConfig(fileSizeThreshold = 1024*1024, maxFileSize = 1024*1024*5, maxRequestSize = 1024*1024*5*5)
public class FileController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(FileController.class);
    private static final String IMAGE = "image";
    private static final String IMAGE_TYPE_PATTERN = "(?i)(png|gif|jpg|jpeg)(?-i)$";
    private static final String INCORRECT_TYPE = "incorrectType";
    private static final String EMPTY_FILE = "emptyFile";
    private static final String APARTMENT = "apartment";
    private static final String LOGIN = "login";
    private static final String APARTMENT_ID = "apartmentId";
    private static final String PAGE = "page";
    private static final String EDIT = "EDIT";
    private static final String SETTINGS = "SETTINGS";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        InputStream inputStream = null;
        Part filePart = request.getPart(IMAGE);
        Pattern pattern = Pattern.compile(IMAGE_TYPE_PATTERN);
        Matcher matcher = pattern.matcher(filePart.getContentType());
        if(!matcher.find()){
            request.setAttribute(INCORRECT_TYPE, true);
        } else {
            if(filePart != null){
                inputStream = filePart.getInputStream();
                try {
                    String pageName = request.getParameter(PAGE);
                    switch (pageName){
                        case EDIT:
                            page = EDIT_ESTATE;
                            long apartmentId = Long.parseLong(request.getParameter(APARTMENT_ID));
                            boolean result = PhotoApartmentServiceImpl.getInstance().add(inputStream, apartmentId);
                            if(result){
                                Apartment apartment = ApartmentServiceImpl.getInstance()
                                        .getApartmentByIdWithOwner(apartmentId) ;
                                HttpSession session = request.getSession(false);
                                session.setAttribute(APARTMENT, apartment);
                            } else {
                                log.warn("photo wasn't added: apartmentId=" + apartmentId);
                            }
                            break;
                        case SETTINGS:
                            page = USER_SETTINGS;
                            String login = request.getParameter(LOGIN);
                            User updatedUser = UserServiceImpl.getInstance().updateUserPhoto(inputStream, login);
                            request.getSession().setAttribute("user", updatedUser);
                            break;
                    }
                } catch (ServiceException e) {
                    log.error("file upload failed: " + e);
                    throw new UnsupportedOperationException(e);
                }
            } else {
                request.setAttribute(EMPTY_FILE, true);
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
