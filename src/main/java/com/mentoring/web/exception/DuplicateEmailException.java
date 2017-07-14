package com.mentoring.web.exception;

/**
 * @author ivanovaolyaa
 * @version 7/14/2017
 */
public class DuplicateEmailException extends Exception {

    public DuplicateEmailException(final String message) {
        super(message);
    }

}
