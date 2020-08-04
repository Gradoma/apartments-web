package by.gradomski.apartments.service.impl;

import by.gradomski.apartments.dao.impl.ApartmentDaoImpl;
import by.gradomski.apartments.entity.Apartment;
import by.gradomski.apartments.entity.ApartmentStatus;
import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.ApartmentService;
import by.gradomski.apartments.service.PhotoApartmentService;
import by.gradomski.apartments.validator.ApartmentValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ApartmentServiceImpl implements ApartmentService {
    private static final Logger log = LogManager.getLogger();
    private static final String FALSE = "false";
    private static final char COMMA = ',';
    private static final char DOT = '.';
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
        Map<String, String> validationResult = ApartmentValidator.isValid(region, city, address, rooms, floor, square,
                year, description);
        if(validationResult.containsValue(FALSE)){
            return validationResult;
        }
        Apartment apartment = new Apartment(owner, region, city, address);
        apartment.setRooms(Integer.parseInt(rooms));
        if(!floor.isBlank()){
            apartment.setFloor(Integer.parseInt(floor));
        }
        if(!square.isBlank()){
            square = square.replace(COMMA, DOT);
            apartment.setSquare(Double.parseDouble(square));
        }
        if(!year.isBlank()){
            apartment.setYear(year);
        }
        if(furniture!=null && !furniture.isBlank()){
            apartment.setFurniture(Boolean.parseBoolean(furniture));
        }
        if(!description.isBlank()){
            apartment.setDescription(description);
        }
        try{
            long newApartmentId = ApartmentDaoImpl.getInstance().add(apartment);
            if(newApartmentId < 0){
                log.warn("apartment wasn't added to DB");
            }
        } catch (DaoException e){
            throw new ServiceException(e);
        } return validationResult;
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
            long apartmentId = apartment.getId();
            PhotoApartmentService photoService = PhotoApartmentServiceImpl.getInstance();
            Map<Long, String> photoMap = photoService.getByApartmentId(apartmentId);
//            if(photoList.isEmpty()){          // todo(def photo here or load logo on page)
//                log.debug("no apartment photo, default photo will be set");
//                String defaultImage = photoService.getDefaultImage();
//                photoList.add(defaultImage);
//            }
            apartment.setPhotoMap(photoMap);
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
            long apartmentId = apartment.getId();
            PhotoApartmentService photoService = PhotoApartmentServiceImpl.getInstance();
            Map<Long, String> photoMap = photoService.getByApartmentId(apartmentId);
//            if(photoList.isEmpty()){
//                log.debug("no apartment photo, default photo will be set");
//                String defaultImage = photoService.getDefaultImage();
//                photoList.add(defaultImage);
//            }
            apartment.setPhotoMap(photoMap);
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
        Apartment apartment = optionalApartment.get();
        long apartmentId = apartment.getId();
        PhotoApartmentService photoService = PhotoApartmentServiceImpl.getInstance();
        Map<Long, String> photoMap = photoService.getByApartmentId(apartmentId);
//        if(photoList.isEmpty()){
//            log.debug("no apartment photo, default photo will be set");
//            String defaultImage = photoService.getDefaultImage();
//            photoList.add(defaultImage);
//        }
        apartment.setPhotoMap(photoMap);
        return apartment;
    }

    @Override
    public List<Apartment> getAllApartments() throws ServiceException {
        List<Apartment> apartmentList;
        try{
            apartmentList = ApartmentDaoImpl.getInstance().findAll();
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return apartmentList;
    }

    @Override
    public Map<String, String> updateApartment(long id, String region, String city, String address, String rooms,
                                               String floor, String square, String year, String furniture,
                                               String description) throws ServiceException {
        Map<String, String> validationResult = ApartmentValidator.isValid(region, city, address, rooms, floor,
                square, year, description);
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
            square = square.replace(COMMA, DOT);
            apartment.setSquare(Double.parseDouble(square));
        }
        if(!year.isBlank()){
            apartment.setYear(year);
        }
        if(!furniture.isBlank()){
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
                case IN_DEMAND:
                    List<Demand> demandList = DemandServiceImpl.getInstance().getActiveDemandsByApartmentId(id);
                    for(Demand demand : demandList){
                        boolean refusingResult = DemandServiceImpl.getInstance().refuseDemand(demand.getId());
                        if(!refusingResult){
                            log.error("can't refuse demand: id=" + demand.getId());
                        }
                    }
                case REGISTERED:
                    flag = ApartmentDaoImpl.getInstance().updateStatusByApartmentId(id, ApartmentStatus.DELETED);
                    if(flag){
                        boolean deletePhotosResult = PhotoApartmentServiceImpl.getInstance()
                                .deleteAllApartmentPhotos(id);
                        if(!deletePhotosResult){
                            log.warn("photos wasn't deleted: apartmentId=" + id);
                        }
                    }
                    break;
                case RENT:
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
