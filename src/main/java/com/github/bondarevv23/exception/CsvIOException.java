package com.github.bondarevv23.exception;

public class CsvIOException extends RuntimeException {
    public CsvIOException(Exception exception) {
        super(exception);
    }
}
