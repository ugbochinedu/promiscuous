package africa.semicolon.promiscuous.exceptions;

public class UserNotFoundException extends PromiscuousBaseException{
    public UserNotFoundException(String message) {
        super(message);
    }
}