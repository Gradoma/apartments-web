package by.gradomski.apartments.service.impl;

import by.gradomski.apartments.dao.impl.AdDaoImpl;
import by.gradomski.apartments.entity.Ad;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.ApartmentStatus;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.AdService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class AdServiceImpl implements AdService {
    private static final Logger log = LogManager.getLogger();
    private static AdServiceImpl instance;

    private AdServiceImpl(){}

    public static AdServiceImpl getInstance(){
        if(instance == null){
            instance = new AdServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean addAdvertisement(String title, User author, String price, long apartmentId) throws ServiceException {
        boolean result;
        if(title == null || title.isBlank() || price == null || price.isBlank()){
            log.debug("title or price null or blank");
            return false;
        }
        BigDecimal decimalPrice = new BigDecimal(price);
        Ad newAd = new Ad(title, author, decimalPrice, apartmentId);
        try{
            result = AdDaoImpl.getInstance().add(newAd);
            log.debug("add new Ad result: " + result);
            if(result){
                ApartmentServiceImpl.getInstance().updateApartmentStatus(apartmentId, ApartmentStatus.IN_DEMAND);
            }
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Ad getAdByApartmentId(long id) throws ServiceException {
        return null;
    }

    @Override
    public boolean updateAd(long id) throws ServiceException {
        return false;
    }

    @Override
    public boolean deleteAd(long id) throws ServiceException {
        return false;
    }
}
