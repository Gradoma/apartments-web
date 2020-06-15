package by.gradomski.apartments.entity;

import by.gradomski.apartments.exception.IncorrectStatusException;

import java.util.Arrays;
import java.util.Optional;

public enum RequestStatus {
    CREATED(1),
    APPROVED(2),
    REFUSED(3),
    CANCELED(4);

    private long value;

    RequestStatus(long value){
        this.value = value;
    }

    public long getValue(){
        return value;
    }

    public static RequestStatus getByValue(long value) throws IncorrectStatusException {
        RequestStatus[] statuses = RequestStatus.values();
        Optional<RequestStatus> optionalRequestStatus = Arrays.stream(statuses).filter(r -> r.getValue() == value).findFirst();
        if (optionalRequestStatus.isEmpty()){
            throw new IncorrectStatusException(value + " type not present in enum");
        }
        return optionalRequestStatus.get();
    }
}
