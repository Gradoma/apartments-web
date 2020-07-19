package by.gradomski.apartments.service.impl;

import by.gradomski.apartments.dao.impl.AdDaoImpl;
import by.gradomski.apartments.dao.impl.ApartmentDaoImpl;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public long addAdvertisement(String title, User author, String price, long apartmentId) throws ServiceException {
        long generatedId;
        if(title == null || title.isBlank() || price == null || price.isBlank()){
            log.debug("title or price null or blank");
            return -1;
        }
        BigDecimal decimalPrice = new BigDecimal(price);
        Ad newAd = new Ad(title, author.getId(), decimalPrice, apartmentId);
        try{
            generatedId = AdDaoImpl.getInstance().add(newAd);
//            if(advertisementId > 0){
//                boolean updateStatusResult = ApartmentServiceImpl.getInstance().
//                        updateApartmentStatus(apartmentId, ApartmentStatus.IN_DEMAND);
//                if(!updateStatusResult){
//                    log.warn("cant update apartment status: apartmentId=" + apartmentId);
//                    boolean deleteAdvertisementResult = AdDaoImpl.getInstance().deleteById(advertisementId);
//                    if(!deleteAdvertisementResult){
//                        result = false;
//                    }
//                }
//            }
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return generatedId;
    }

    @Override
    public Ad getAdByApartmentId(long id) throws ServiceException {
        Optional<Ad> optionalAd;
        try {
            optionalAd = AdDaoImpl.getInstance().findByApartmentId(id);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        if(optionalAd.isEmpty()){
            throw new ServiceException("ad wasn't found, id: " + id);
        }
        return optionalAd.get();
    }

    @Override
    public Ad getAdById(long id) throws ServiceException {
        Optional<Ad> optionalAd;
        try {
            optionalAd = AdDaoImpl.getInstance().findById(id);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        if(optionalAd.isEmpty()){
            throw new ServiceException("ad wasn't found, id: " + id);
        }
        return optionalAd.get();
    }

    @Override
    public List<Ad> getAllVisible() throws ServiceException {
        List<Ad> resultList;
        try{
            resultList = AdDaoImpl.getInstance().findAllVisible();
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return resultList;
    }

    @Override
    public boolean updateAd(long id) throws ServiceException {
        return false;
    }

    @Override
    public boolean changeVisibility(long id) throws ServiceException {
        boolean flag;
        try{
            Ad ad = getAdById(id);
            boolean currentVisibility = ad.isVisible();
            log.debug("current visibility" + currentVisibility);
            ad.setVisibility(!currentVisibility);
            log.debug("new visibility: " + ad.isVisible());
            flag = AdDaoImpl.getInstance().update(ad);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public boolean deleteAd(long id) throws ServiceException {
        boolean flag;
        try{
            flag = AdDaoImpl.getInstance().deleteById(id);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }
}
