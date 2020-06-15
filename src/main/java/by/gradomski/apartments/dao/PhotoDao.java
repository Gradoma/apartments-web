package by.gradomski.apartments.dao;

import java.io.File;
import java.util.List;

public interface PhotoDao extends AbstractDao {
    boolean add(File photo);
    List<File> findByApartment(long id);
    File update(File photo);
    boolean deletePhotoById(long id);
}
