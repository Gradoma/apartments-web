package by.gradomski.apartments.service.impl;

import by.gradomski.apartments.dao.impl.ApartmentDaoImpl;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.ApartmentService;
import by.gradomski.apartments.service.validator.ApartmentValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

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
        Map<String, String> validationResult = ApartmentValidator.isValid(region, city, address, rooms);
        if(validationResult.containsValue(FALSE)){
            return validationResult;
        }
        Apartment apartment = new Apartment(owner, region, city, address);
        apartment.setRooms(Integer.parseInt(rooms));
        log.warn("floor: " + floor);                    //DELETE!!!!
        if(!floor.isBlank()){
            apartment.setFloor(Integer.parseInt(floor));        //TODO(can set minus values)  SOLVE!!!!
        }
        if(!square.isBlank()){
            log.debug("square for parsing: " + square);
            apartment.setSquare(Double.parseDouble(square));
        }
        if(!year.isBlank()){                //TODO(check year - 4 digit)
            apartment.setYear(year);
        }
        if(!year.isBlank()){
            apartment.setFurniture(Boolean.parseBoolean(furniture));        //TODO(Don't set false)
        }
        if(!description.isBlank()){
            apartment.setDescription(description);        //TODO(Don't set false)
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
}
