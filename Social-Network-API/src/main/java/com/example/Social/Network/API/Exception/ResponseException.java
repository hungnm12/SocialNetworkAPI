package com.example.Social.Network.API.Exception;

public class ResponseException extends Exception {
    public ResponseException(String message) {
        super(message);
    }

    public ResponseException(Throwable t) {
        super(t);
    }

    public ResponseException(String message, Throwable t) {
        super(message, t);
    }

    public ResponseException() {
        super();
    }
}