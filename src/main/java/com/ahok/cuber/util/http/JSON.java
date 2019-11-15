package com.ahok.cuber.util.http;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JSON {
    public static String toJSON (Object object) {
        try {
            return new ObjectMapper().writer().writeValueAsString(object);
        } catch (IOException e) {
            return object.toString();
        }
    }
}
