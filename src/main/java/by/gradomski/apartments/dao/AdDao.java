package by.gradomski.apartments.dao;

import by.gradomski.apartments.entity.Ad;

import java.util.List;

public interface AdDao extends BaseDao {
    boolean add(Ad ad);
    List<Ad> findAll();
    List<Ad> findByAuthor(long id);
    Ad update(Ad ad);
    boolean deleteById(long id);
}
