package by.gradomski.apartments.dao.impl.constant;

public enum UserTable {
    ID_USER("idUser"),
    ID_ROLE("idRole"),
    LOGIN("login"),
    PASSWORD("password"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    BIRTHDAY("birthday"),
    GENDER("gender"),
    PHONE("phone"),
    PHOTO("photo"),
    REGISTRATION_DATE("registrationDate"),
    MAIL_ADDRESS("mailAddress"),
    VISIBILITY("visibility");

    private String value;

    UserTable(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
