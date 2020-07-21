package by.gradomski.apartments.service.impl;

import by.gradomski.apartments.dao.impl.PhotoApartmentDaoImpl;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.PhotoApartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.List;

public class PhotoApartmentServiceImpl implements PhotoApartmentService {
    private static final Logger log = LogManager.getLogger();
    private static PhotoApartmentServiceImpl instance;

    private PhotoApartmentServiceImpl(){}

    public static PhotoApartmentServiceImpl getInstance() {
        if(instance == null){
            instance = new PhotoApartmentServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean add(InputStream inputStream, long apartmentId) throws ServiceException {
        boolean result;
        try {
            result = PhotoApartmentDaoImpl.getInstance().add(inputStream, apartmentId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<String> getByApartmentId(long apartmentId) throws ServiceException {
        List<String> resultList;
        try{
            resultList = PhotoApartmentDaoImpl.getInstance().findByApartment(apartmentId);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return resultList;
    }

    @Override
    public String getDefaultImage() throws ServiceException {
        String image;
        try{
            image = PhotoApartmentDaoImpl.getInstance().findDefaultImage();
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return image;
    }

    @Override
    public boolean deleteAllApartmentPhotos(long apartmentId) throws ServiceException {
        boolean result;
        try {
            result = PhotoApartmentDaoImpl.getInstance().deletePhotosByApartmentId(apartmentId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }
}
