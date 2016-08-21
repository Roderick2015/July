package com.roderick.july.controller;

/**
 * Created by Roderick on 2016/8/20.
 */
public class ThriftServletException extends Exception {
    public ThriftServletException(String message) {
        super(message);
    }

    public ThriftServletException(String message, Throwable cause) {
        super(message, cause);
    }
}
