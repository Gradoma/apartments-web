package by.gradomski.apartments.service;

import by.gradomski.apartments.entity.Request;
import by.gradomski.apartments.entity.RequestStatus;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;

import java.util.List;

public interface RequestService {
    boolean addRequest(User author, String apartmentIdString, String expectedDateString,
                       String description) throws ServiceException;
    List<Request> getRequestsByApartmentId(long id) throws ServiceException;
    List<Request> getRequestsByApplicantId(long id) throws ServiceException;
    boolean approveRequest(long approvingRequestId, List<Request> apartmentRequestList) throws ServiceException;
    boolean refuseRequest(long id) throws ServiceException;
    boolean cancelRequest(long id) throws ServiceException;
}
