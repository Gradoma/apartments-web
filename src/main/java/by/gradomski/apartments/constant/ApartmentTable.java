package by.gradomski.apartments.constant;

public enum ApartmentTable {
    ID_APARTMENT("idApartment"),
    REGION("region"),
    CITY("city"),
    ADDRESS("address"),
    ROOMS("rooms"),
    SQUARE("square"),
    FLOOR ("floor"),
    AGE("age"),
    YEAR("year"),
    FURNITURE("furniture"),
    DESCRIPTION("description"),
    ID_TENANT("idTenant"),
    ID_STATUS("idStatus"),
    REGISTRATION_DATE("registrationDate"),
    VISIBILITY("visibility");

    private String value;

    ApartmentTable(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
