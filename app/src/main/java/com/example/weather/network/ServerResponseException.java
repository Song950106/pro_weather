package com.example.weather.network;

import java.io.IOException;

public class ServerResponseException extends IOException {
    public ServerResponseException(String message) {
        super(message);
    }
}
