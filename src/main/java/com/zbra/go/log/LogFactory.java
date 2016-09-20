package com.zbra.go.log;

public class LogFactory {

    private LogFactory() {
    }

    public static Log createLog(Class<?> clazz) {
        return new Slf4jImpl(clazz);
    }

}
