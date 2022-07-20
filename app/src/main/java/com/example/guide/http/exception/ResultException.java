package com.example.guide.http.exception;


import com.example.guide.http.model.HttpData;

public class ResultException extends Exception{

    public ResultException(String message, HttpData<?> data) {
        super(message);
    }

}
