package org.example.synth.core.command.exceptions;

public class QueueOverflowException extends Exception {

    public QueueOverflowException(String message, Exception e) {
        super(message, e);
    }
}
