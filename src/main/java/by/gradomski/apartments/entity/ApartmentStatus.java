package by.gradomski.apartments.entity;

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
}
