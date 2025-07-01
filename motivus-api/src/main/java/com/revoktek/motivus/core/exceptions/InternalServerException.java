package com.revoktek.motivus.core.exceptions;

public class InternalServerException extends RuntimeException  {

    private final String message;

    public InternalServerException(String message) {
        super(message);
        this.message = message;
    }

    public InternalServerException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

}
