package ru.itmo.scs.exceptions;

/**
 * Created by i.isaev on 15.04.2021.
 */
public class InvalidPropertiesException extends InvalidCoreException {

    public InvalidPropertiesException(String message) {
        super(message);
    }

    public InvalidPropertiesException() {
        super.message = "Empty file.";
    }
}
