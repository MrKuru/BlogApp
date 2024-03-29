package com.springboot.blog.dto;

import java.util.Date;

public class ErrorDetails {
    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    private Date timestamp;
    private String message;
    private String details;

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }
}


