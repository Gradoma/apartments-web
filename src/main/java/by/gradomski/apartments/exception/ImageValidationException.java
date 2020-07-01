package by.gradomski.apartments.exception;

public class ImageValidationException extends Exception {
    static final long serialVersionUID = 1L;
    public ImageValidationException(){
        super();
    }

    public ImageValidationException(String message){
        super(message);
    }

    public ImageValidationException(Exception e){
        super(e);
    }

    public ImageValidationException(String message, Exception e){
        super(message, e);
    }
}
