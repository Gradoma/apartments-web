package by.gradomski.apartments.service.impl;

import by.gradomski.apartments.dao.impl.RequestDaoImpl;
import by.gradomski.apartments.dao.impl.UserDaoImpl;
import by.gradomski.apartments.entity.Request;
import by.gradomski.apartments.entity.RequestStatus;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.DaoException;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.service.RequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class RequestServiceImpl implements RequestService {
    private static final Logger log = LogManager.getLogger();
    private static RequestServiceImpl instance;

    private RequestServiceImpl(){}

    public static RequestServiceImpl getInstance(){
        if(instance == null){
            instance = new RequestServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean addRequest(User author, String apartmentIdString, String expectedDateString,
                              String description) throws ServiceException {
        boolean flag;
        long apartmentId = Long.parseLong(apartmentIdString);
        LocalDate expectedDate = LocalDate.parse(expectedDateString);
        Request newRequest = new Request(author, apartmentId, expectedDate);
        if(description != null){
            newRequest.setDescription(description);
        }
        try {
            flag = RequestDaoImpl.getInstance().add(newRequest);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public Request getRequestById(long id) throws ServiceException {
        Optional<Request> optionalRequest;
        try{
            optionalRequest = RequestDaoImpl.getInstance().findById(id);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        if(optionalRequest.isEmpty()){
            log.error("request wasn't found: id=" + id);
            throw new ServiceException("request wasn't found: id=" + id);
        }
        return optionalRequest.get();
    }

    @Override
    public List<Request> getAll() throws ServiceException {
        List<Request> resultList;
        try{
            resultList = RequestDaoImpl.getInstance().findAll();
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return resultList;
    }

    @Override
    public List<Request> getActiveRequestsByApartmentId(long apartmentId) throws ServiceException {
        List<Request> resultList;
        try{
            resultList = RequestDaoImpl.getInstance().findByApartment(apartmentId);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return resultList;
    }

    @Override
    public List<Request> getRequestsByApplicantId(long applicantId) throws ServiceException {
        List<Request> resultList;
        try{
            resultList = RequestDaoImpl.getInstance().findByApplicant(applicantId);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return resultList;
    }

    @Override
    public boolean approveRequestFromList(long approvingRequestId, List<Request> apartmentRequestList) throws ServiceException {
        boolean flag;
        Optional<Request> optionalRequest = containsRequest(apartmentRequestList, approvingRequestId);
        if(optionalRequest.isPresent()) {
            Request requestForApproving = optionalRequest.get();
            apartmentRequestList.remove(requestForApproving);
            try {
                boolean approvingResult = RequestDaoImpl.getInstance()
                        .updateStatusById(requestForApproving.getId(), RequestStatus.APPROVED);
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
    public boolean refuseRequest(long id) throws ServiceException {
        boolean flag = false;
        try{
            flag = RequestDaoImpl.getInstance().updateStatusById(id, RequestStatus.REFUSED);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public boolean cancelRequest(long id) throws ServiceException {
        boolean flag = false;
        try{
            flag = RequestDaoImpl.getInstance().updateStatusById(id, RequestStatus.CANCELED);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public boolean archiveRequest(long id) throws ServiceException {
        boolean flag = false;
        try{
            flag = RequestDaoImpl.getInstance().updateStatusById(id, RequestStatus.ARCHIVED);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    private Optional<Request> containsRequest(List<Request> requestList, long requestId){
        Optional<Request> optionalRequest = requestList.stream()
                .filter(request -> request.getId() == requestId)
                .findFirst();
        return optionalRequest;
    }
}
