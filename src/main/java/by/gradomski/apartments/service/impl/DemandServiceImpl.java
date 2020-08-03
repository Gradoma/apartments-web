package by.gradomski.apartments.service.impl;

import by.gradomski.apartments.dao.impl.DemandDaoImpl;
import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.entity.DemandStatus;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.DemandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DemandServiceImpl implements DemandService {
    private static final Logger log = LogManager.getLogger();
    private static DemandServiceImpl instance;

    private DemandServiceImpl(){}

    public static DemandServiceImpl getInstance(){
        if(instance == null){
            instance = new DemandServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean addDemand(User author, String apartmentIdString, String expectedDateString,
                             String description) throws ServiceException {
        boolean flag;
        long apartmentId = Long.parseLong(apartmentIdString);
        LocalDate expectedDate = LocalDate.parse(expectedDateString);
        Demand newDemand = new Demand(author, apartmentId, expectedDate);
        if(description != null){
            newDemand.setDescription(description);
        }
        try {
            flag = DemandDaoImpl.getInstance().add(newDemand);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public Demand getDemandById(long id) throws ServiceException {
        Optional<Demand> optionalDemand;
        try{
            optionalDemand = DemandDaoImpl.getInstance().findById(id);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        if(optionalDemand.isEmpty()){
            log.error("demand wasn't found: id=" + id);
            throw new ServiceException("demand wasn't found: id=" + id);
        }
        return optionalDemand.get();
    }

    @Override
    public List<Demand> getAll() throws ServiceException {
        List<Demand> resultList;
        try{
            resultList = DemandDaoImpl.getInstance().findAll();
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return resultList;
    }

    @Override
    public List<Demand> getActiveDemandsByApartmentId(long apartmentId) throws ServiceException {
        List<Demand> resultList;
        try{
            resultList = DemandDaoImpl.getInstance().findByApartment(apartmentId);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return resultList;
    }

    @Override
    public List<Demand> getDemandsByApplicantId(long applicantId) throws ServiceException {
        List<Demand> resultList;
        try{
            resultList = DemandDaoImpl.getInstance().findByApplicant(applicantId);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return resultList;
    }

    @Override
    public boolean approveDemandFromList(long approvingDemandId, List<Demand> apartmentDemandList) throws ServiceException {
        boolean flag;
        Optional<Demand> optionalDemand = containsDemand(apartmentDemandList, approvingDemandId);
        if(optionalDemand.isPresent()) {
            Demand demandForApproving = optionalDemand.get();
            apartmentDemandList.remove(demandForApproving);
            try {
                boolean approvingResult = DemandDaoImpl.getInstance()
                        .updateStatusById(demandForApproving.getId(), DemandStatus.APPROVED);
                if(!approvingResult){
                    return false;
                }
                log.debug("end method body");
                flag = true;
            } catch (DaoException e){
                throw new ServiceException(e);
            }
        } else {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean refuseDemand(long id) throws ServiceException {
        boolean flag = false;
        try{
            flag = DemandDaoImpl.getInstance().updateStatusById(id, DemandStatus.REFUSED);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public boolean cancelDemand(long id) throws ServiceException {
        boolean flag = false;
        try{
            flag = DemandDaoImpl.getInstance().updateStatusById(id, DemandStatus.CANCELED);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public boolean archiveDemand(long id) throws ServiceException {
        boolean flag = false;
        try{
            flag = DemandDaoImpl.getInstance().updateStatusById(id, DemandStatus.ARCHIVED);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    private Optional<Demand> containsDemand(List<Demand> demandList, long demandId){
        Optional<Demand> optionalDemand = demandList.stream()
                .filter(demand -> demand.getId() == demandId)
                .findFirst();
        return optionalDemand;
    }
}
