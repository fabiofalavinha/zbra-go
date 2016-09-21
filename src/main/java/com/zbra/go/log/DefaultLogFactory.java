package com.zbra.go.log;

import org.springframework.stereotype.Component;

@Component
public class DefaultLogFactory implements LogFactory {

    private DefaultLogFactory() {
    }

    public Log createLog(Class<?> clazz) {
        return new Slf4jImpl(clazz);
    }

}
