package by.gradomski.apartments.exception;

public class IncorrectStatusException extends Exception {
    static final long serialVersionUID = 1L;

    public IncorrectStatusException(){
        super();
    }

    public IncorrectStatusException(String message){
        super(message);
    }

    public IncorrectStatusException(Exception e){
        super(e);
    }

    public IncorrectStatusException(String message, Exception e){
        super(message, e);
    }
}
