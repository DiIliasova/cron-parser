package main.com.company.exceptions;

public class CronValidationException extends Exception {

    public CronValidationException(String message){
        super(message);
    }
}
