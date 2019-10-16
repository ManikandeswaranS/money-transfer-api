package com.org.transfers.exception;

/**
 * The type Account exception.
 */
public class AccountException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Account exception.
     *
     * @param msg the msg
     */
    public AccountException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Account exception.
     *
     * @param msg   the msg
     * @param cause the cause
     */
    public AccountException(String msg, Throwable cause) {
        super(msg, cause);
    }
}