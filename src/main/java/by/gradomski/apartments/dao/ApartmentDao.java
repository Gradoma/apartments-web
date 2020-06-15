package by.gradomski.apartments.dao;

import by.gradomski.apartments.entity.Apartment;

import java.util.List;

public interface ApartmentDao extends AbstractDao {
    boolean add(Apartment apartment);
    List<Apartment> findApartmentsByOwner(long id);
    List<Apartment> findApartmentsByTenant(long id);
    List<Apartment> findAll();
    Apartment update(Apartment apartment);
    boolean deleteApartmentById(long id);
}
