package by.gradomski.apartments.util;

import by.gradomski.apartments.entity.Ad;

import java.util.Comparator;

public class AdvertisementComparator implements Comparator<Ad> {
    @Override
    public int compare(Ad o1, Ad o2) {
        return -o1.getCreationDate().compareTo(o2.getCreationDate());
    }
}
