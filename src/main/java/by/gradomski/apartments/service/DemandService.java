package by.gradomski.apartments.service;

import by.gradomski.apartments.entity.Demand;
import by.gradomski.apartments.entity.User;
import by.gradomski.apartments.exception.ServiceException;

import java.util.List;

public interface DemandService {
    boolean addDemand(User author, String apartmentIdString, String expectedDateString,
                      String description) throws ServiceException;
    List<Demand> getAll() throws ServiceException;
    Demand getDemandById(long id) throws ServiceException;
    List<Demand> getActiveDemandsByApartmentId(long apartmentId) throws ServiceException;
    List<Demand> getDemandsByApplicantId(long applicantId) throws ServiceException;
    boolean approveDemandFromList(long approvingRequestId, List<Demand> apartmentDemandList) throws ServiceException;
    boolean refuseDemand(long id) throws ServiceException;
    boolean cancelDemand(long id) throws ServiceException;
    boolean archiveDemand(long id) throws ServiceException;
}
