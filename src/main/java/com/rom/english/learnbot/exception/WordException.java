package com.rom.english.learnbot.exception;

public class WordException extends RuntimeException {

    public WordException(String message) {
        super(message);
    }

    public WordException(String message, Throwable cause) {
        super(message, cause);
    }

    public WordException(Throwable cause) {
        super(cause);
    }
}
