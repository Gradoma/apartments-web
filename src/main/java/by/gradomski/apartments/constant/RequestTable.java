package by.gradomski.apartments.constant;

public enum RequestTable {
    ID_REQUEST("idRequest"),
    ID_APPLICANT("idApplicant"),
    ID_APARTMENT("idApartment"),
    EXPECTED_DATE("expectedDate"),
    CREATION_DATE("creationDate"),
    DESCRIPTION("description"),
    ID_STATUS_REQUEST("idStatusReq");

    private String value;

    RequestTable(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
