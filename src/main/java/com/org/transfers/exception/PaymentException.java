package com.org.transfers.exception;

/**
 * The type Payment exception.
 */
public class PaymentException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Payment exception.
     *
     * @param msg the msg
     */
    public PaymentException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Payment exception.
     *
     * @param msg   the msg
     * @param cause the cause
     */
    public PaymentException(String msg, Throwable cause) {
        super(msg, cause);
    }
}