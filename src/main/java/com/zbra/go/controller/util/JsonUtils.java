package com.zbra.go.controller.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {

    public static <R> R fromJson(String json, Class<R> clazz) throws IOException {
        return new ObjectMapper().readValue(json, clazz);
    }

    private JsonUtils() {
    }

}
