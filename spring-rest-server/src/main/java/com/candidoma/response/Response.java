package com.candidoma.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

import java.net.InetAddress;

@Getter
@Setter
public class Response<T>{
    private String timestamp;
    private String hostName;
    private String hostAddress;

    private T body;

    public Response() {
        try {
            hostAddress=InetAddress.getLocalHost().getHostAddress();
            hostName=InetAddress.getLocalHost().getHostName();
        }catch (Exception e){}
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
    }
}
