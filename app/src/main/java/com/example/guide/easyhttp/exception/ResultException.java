package com.example.guide.easyhttp.exception;


import com.example.guide.easyhttp.model.HttpData;

public class ResultException extends Exception{

    public ResultException(String message, HttpData<?> data) {
        super(message);
    }

}
