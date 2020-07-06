package by.gradomski.apartments.entity;

import by.gradomski.apartments.exception.IncorrectStatusException;

import java.util.Arrays;
import java.util.Optional;

public enum ApartmentStatus {
    REGISTERED(1),
    IN_DEMAND(2),
    RENT(3),
    DELETED(4);

    private int value;

    ApartmentStatus(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static ApartmentStatus getByValue(long value) throws IncorrectStatusException {
        ApartmentStatus[] statuses = ApartmentStatus.values();
        Optional<ApartmentStatus> optionalApartmentStatus = Arrays.stream(statuses).filter(r -> r.getValue() == value).findFirst();
        if (optionalApartmentStatus.isEmpty()){
            throw new IncorrectStatusException(value + " type not present in enum");
        }
        return optionalApartmentStatus.get();
    }
}
