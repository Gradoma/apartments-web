package by.gradomski.apartments.constant;

public enum ApartmentTable {
    NAME("appartment"),
    ID_APARTMENT("idAppartment"),
    REGION("region"),
    CITY("city"),
    ADDRESS("address"),
    ROOMS("rooms"),
    SQUARE("square"),
    FLOOR ("floor"),
    AGE("age"),
    YEAR("year"),
    FURNITURE("furniture");


    private String value;

    ApartmentTable(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
