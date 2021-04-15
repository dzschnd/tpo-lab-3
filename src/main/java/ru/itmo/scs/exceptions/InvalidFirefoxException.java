package ru.itmo.scs.exceptions;

/**
 * Created by i.isaev on 15.04.2021.
 */
public class InvalidFirefoxException extends InvalidCoreException {

    public InvalidFirefoxException(String message) {
        super(message);
    }

    public InvalidFirefoxException() {
        super.message = "Configuration for running Firefox not found.";
    }
}
