package by.gradomski.apartments.entity;

import by.gradomski.apartments.exception.IncorrectStatusException;

import java.util.Arrays;
import java.util.Optional;

public enum DemandStatus {
    CREATED(1),
    APPROVED(2),
    REFUSED(3),
    CANCELED(4),
    ARCHIVED(5);

    private long value;

    DemandStatus(long value){
        this.value = value;
    }

    public long getValue(){
        return value;
    }

    public static DemandStatus getByValue(long value) throws IncorrectStatusException {
        DemandStatus[] statuses = DemandStatus.values();
        Optional<DemandStatus> optionalRequestStatus = Arrays.stream(statuses).filter(r -> r.getValue() == value).findFirst();
        if (optionalRequestStatus.isEmpty()){
            throw new IncorrectStatusException(value + " type not present in enum");
        }
        return optionalRequestStatus.get();
    }
}
