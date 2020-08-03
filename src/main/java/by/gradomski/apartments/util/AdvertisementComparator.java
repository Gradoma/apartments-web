package by.gradomski.apartments.util;

import by.gradomski.apartments.entity.Advertisement;

import java.util.Comparator;

public class AdvertisementComparator implements Comparator<Advertisement> {
    @Override
    public int compare(Advertisement o1, Advertisement o2) {
        return -o1.getCreationDate().compareTo(o2.getCreationDate());
    }
}
