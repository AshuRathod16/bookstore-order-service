package com.bridgelabz.bookstoreorderservice.exception;


import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class OrderException extends RuntimeException{
    private int statusCode;
    private String statusMessage;

    public OrderException(int i, String book_id_is_not_present) {
    }
}
