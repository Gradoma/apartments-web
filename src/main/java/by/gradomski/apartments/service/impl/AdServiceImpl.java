package by.gradomski.apartments.service.impl;

import by.gradomski.apartments.dao.impl.AdDaoImpl;
import by.gradomski.apartments.entity.Advertisement;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.AdService;
import by.gradomski.apartments.util.AdvertisementComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class AdServiceImpl implements AdService {
    private static final Logger log = LogManager.getLogger();
    private static AdServiceImpl instance;
    private static final char COMMA = ',';
    private static final char DOT = '.';

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
        price = price.replace(COMMA, DOT);
        BigDecimal decimalPrice = new BigDecimal(price);
        Advertisement newAdvertisement = new Advertisement(title, author.getId(), decimalPrice, apartmentId);
        try{
            generatedId = AdDaoImpl.getInstance().add(newAdvertisement);
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
    public List<Advertisement> getAll() throws ServiceException {
        List<Advertisement> resultList;
        try{
            resultList = AdDaoImpl.getInstance().findAll();
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return resultList;
    }

    @Override
    public Advertisement getAdByApartmentId(long id) throws ServiceException {
        Optional<Advertisement> optionalAd;
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
    public Advertisement getAdById(long id) throws ServiceException {
        Optional<Advertisement> optionalAd;
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
    public List<Advertisement> getAllVisible() throws ServiceException {
        List<Advertisement> resultList;
        try{
            resultList = AdDaoImpl.getInstance().findAllVisible();
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        resultList.sort(new AdvertisementComparator());
        return resultList;
    }

    @Override
    public boolean updateAd(Advertisement advertisement, String title, String price) throws ServiceException {
        boolean flag;
        if(title == null || title.isBlank() || price == null || price.isBlank()){
            log.debug("title or price null or blank");
            return false;
        }
        BigDecimal decimalPrice = new BigDecimal(price);
        advertisement.setTitle(title);
        advertisement.setPrice(decimalPrice);
        try{
            flag = AdDaoImpl.getInstance().update(advertisement);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public boolean changeVisibility(long id) throws ServiceException {
        boolean flag;
        try{
            Advertisement advertisement = getAdById(id);
            boolean currentVisibility = advertisement.isVisible();
            log.debug("current visibility" + currentVisibility);
            advertisement.setVisibility(!currentVisibility);
            log.debug("new visibility: " + advertisement.isVisible());
            flag = AdDaoImpl.getInstance().update(advertisement);
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
