package ru.itmo.scs.exceptions;

/**
 * Created by i.isaev on 15.04.2021.
 */
public class InvalidChromeException extends InvalidCoreException {

    public InvalidChromeException(String message) {
        super(message);
    }

    public InvalidChromeException() {
        super.message = "Configuration for running Chrome not found.";
    }

}
