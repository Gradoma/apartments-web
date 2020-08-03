package by.gradomski.apartments.util;

import by.gradomski.apartments.entity.Advertisement;

import java.util.List;

public class PageCounter {
    private static final double ON_PAGE = 5.0;

    public static int countPages(List<Advertisement> advertisementList){
        int size = advertisementList.size();
        return (int) Math.ceil(size/ON_PAGE);
    }
}
