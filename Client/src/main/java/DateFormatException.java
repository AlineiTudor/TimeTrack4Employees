public class DateFormatException extends Exception {
    String message;
    public DateFormatException() {
        super("Invalid date format");
    }
    public DateFormatException(String message){
        super(message);
    }
}
