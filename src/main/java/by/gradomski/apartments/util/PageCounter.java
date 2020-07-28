package by.gradomski.apartments.util;

import by.gradomski.apartments.entity.Ad;

import java.util.List;

public class PageCounter {
    private static final double ON_PAGE = 5.0;

    public static int countPages(List<Ad> advertisementList){
        int size = advertisementList.size();
        return (int) Math.ceil(size/ON_PAGE);
    }
}
