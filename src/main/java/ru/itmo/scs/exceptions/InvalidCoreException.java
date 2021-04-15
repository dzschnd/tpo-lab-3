package ru.itmo.scs.exceptions;

/**
 * Created by i.isaev on 15.04.2021.
 */
public class InvalidCoreException extends IllegalArgumentException {

    protected String message;

    public InvalidCoreException(String message) {
        this.message = message;
    }

    public InvalidCoreException() {
        message = "Invalid.";
    }

    public String getMessage() {
        return message;
    }
}
