package utility;

public class WrongInputException extends Exception{

    public WrongInputException() {

    }

    @Override
    public String toString() {
        return "Wrong Input Entered";
    }

    @Override
    public String getMessage() {
        return "Wrong Input Entered";
    }
}
