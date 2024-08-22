package com.simple.bank_system.service.exception;

public class IdNotFoundException extends BusinessException{

    public IdNotFoundException(Integer id) {
        super("A user with this " + id + " not exists.");
    }
}
