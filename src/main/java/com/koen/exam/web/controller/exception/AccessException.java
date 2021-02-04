package com.koen.exam.web.controller.exception;

public class AccessException extends Exception{
    public AccessException() {
        super("Нет прав доступа");
    }
}
