package by.gradomski.apartments.exception;

public class IncorrectRoleException extends Exception {
    static final long serialVersionUID = 1L;

    public IncorrectRoleException(){
        super();
    }

    public IncorrectRoleException(String message){
        super(message);
    }

    public IncorrectRoleException(Exception e){
        super(e);
    }

    public IncorrectRoleException(String message, Exception e){
        super(message, e);
    }
}
