package io.github.burakkaygusuz.exceptions;

public class UnsupportedBrowserException extends IllegalStateException {

    public UnsupportedBrowserException(String errorMessage) {
        super(errorMessage);
    }
}
