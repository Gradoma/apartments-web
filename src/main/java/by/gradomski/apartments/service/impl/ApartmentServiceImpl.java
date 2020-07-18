package by.gradomski.apartments.service.impl;

import by.gradomski.apartments.dao.impl.ApartmentDaoImpl;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.ApartmentStatus;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.ApartmentService;
import by.gradomski.apartments.service.validator.ApartmentValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ApartmentServiceImpl implements ApartmentService {
    private static final Logger log = LogManager.getLogger();
    private static final String FALSE = "false";
    private static ApartmentServiceImpl instance;

    private ApartmentServiceImpl(){}

    public static ApartmentServiceImpl getInstance(){
        if(instance == null){
            instance = new ApartmentServiceImpl();
        }
        return instance;
    }

    @Override
    public Map<String, String> addApartment(User owner, String region, String city, String address, String rooms, String floor,
                                            String square, String year, String furniture, String description) throws ServiceException {
        log.debug("owner " + owner);
        Map<String, String> validationResult = ApartmentValidator.isValid(region, city, address, rooms, floor, square, year);
        if(validationResult.containsValue(FALSE)){
            return validationResult;
        }
        Apartment apartment = new Apartment(owner, region, city, address);
        apartment.setRooms(Integer.parseInt(rooms));
        if(!floor.isBlank()){
            apartment.setFloor(Integer.parseInt(floor));
        }
        if(!square.isBlank()){
            apartment.setSquare(Double.parseDouble(square));
        }
        if(!year.isBlank()){
            apartment.setYear(year);
        }
        if(!year.isBlank()){
            apartment.setFurniture(Boolean.parseBoolean(furniture));
        }
        if(!description.isBlank()){
            apartment.setDescription(description);
        }
        try{
            boolean result = ApartmentDaoImpl.getInstance().add(apartment);
            if(!result){
                log.warn("apartment wasn't added to DB");
            }
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return validationResult;
    }

    @Override
    public List<Apartment> getApartmentsByOwner(long id) throws ServiceException {
        List<Apartment> apartmentList;
        User owner;
        try {
            apartmentList = ApartmentDaoImpl.getInstance().findApartmentsByOwner(id);
            owner = UserServiceImpl.getInstance().getUserById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        for(Apartment apartment : apartmentList){
            apartment.setOwner(owner);
        }
        return apartmentList;
    }

    @Override
    public List<Apartment> getApartmentsByTenant(long id) throws ServiceException {
        List<Apartment> apartmentList;
        User tenant;
        try {
            apartmentList = ApartmentDaoImpl.getInstance().findApartmentsByTenant(id);
            tenant = UserServiceImpl.getInstance().getUserById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        for(Apartment apartment : apartmentList){
            apartment.setTenant(tenant);
        }
        return apartmentList;
    }

    @Override
    public Apartment getApartmentByIdWithOwner(long id) throws ServiceException {
        Optional<Apartment> optionalApartment;
        try {
            optionalApartment = ApartmentDaoImpl.getInstance().findApartmentByIdWithOwner(id);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        if(optionalApartment.isEmpty()){
            throw new ServiceException("apartment wasn't found, id: " + id);
        }
        return optionalApartment.get();
    }

    @Override
    public Map<String, String> updateApartment(long id, String region, String city, String address, String rooms,
                                               String floor, String square, String year, String furniture,
                                               String description) throws ServiceException {
        Map<String, String> validationResult = ApartmentValidator.isValid(region, city, address, rooms, floor, square, year);
        if(validationResult.containsValue(FALSE)){
            return validationResult;
        }
        User owner = null;
        Apartment apartment = new Apartment(owner, region, city, address);
        apartment.setId(id);
        apartment.setRooms(Integer.parseInt(rooms));
        if(!floor.isBlank()){
            apartment.setFloor(Integer.parseInt(floor));
        }
        if(!square.isBlank()){
            apartment.setSquare(Double.parseDouble(square));
        }
        if(!year.isBlank()){
            apartment.setYear(year);
        }
        if(!year.isBlank()){
            apartment.setFurniture(Boolean.parseBoolean(furniture));
        }
        if(!description.isBlank()){
            apartment.setDescription(description);
        }
        try{
            ApartmentDaoImpl.getInstance().update(apartment);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return validationResult;
    }

    @Override
    public boolean updateApartmentStatus(long id, ApartmentStatus status) throws ServiceException {
        boolean result;
        try{
            result = ApartmentDaoImpl.getInstance().updateStatusByApartmentId(id, status);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean updateTenant(long apartmentId, long tenantId) throws ServiceException {
        boolean result;
        try{
            result = ApartmentDaoImpl.getInstance().updateTenantByApartmentId(apartmentId, tenantId);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean deleteApartment(long id) throws ServiceException {
        boolean flag = false;
        try {
            ApartmentStatus currentStatus = ApartmentDaoImpl.getInstance().findStatusByApartmentId(id);
            switch (currentStatus){
                case REGISTERED:
                    flag = ApartmentDaoImpl.getInstance().updateStatusByApartmentId(id, ApartmentStatus.DELETED);
                    break;
                case IN_DEMAND:
                    //TODO(requestService - getAllRequests - iterate: request.changeStatus(DECLINE) + adService - deleteAd)
                    break;
                case RENT:
                    // TODO
                    log.warn("try to delete rent apartment");
                    break;
                case DELETED:
                    log.warn("impossible operation: apartment already has been deleted: id = " + id);
                    break;
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
