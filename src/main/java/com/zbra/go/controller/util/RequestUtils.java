package com.zbra.go.controller.util;


import javax.servlet.http.HttpServletRequest;

public final class RequestUtils {

    public static final String HEADER_PLAYER_KEY = "Player-Key";

    public static String getPlayerKey(HttpServletRequest request) {
        return request.getHeader(HEADER_PLAYER_KEY);
    }

    private RequestUtils() {
    }
}
