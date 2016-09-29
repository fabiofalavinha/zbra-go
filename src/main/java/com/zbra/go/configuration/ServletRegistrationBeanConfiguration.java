package com.zbra.go.configuration;

import com.zbra.go.controller.MediaStreamServlet;
import com.zbra.go.service.DefaultImageService;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cmaia on 29/09/16
 */
@Configuration
public class ServletRegistrationBeanConfiguration {
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        final MediaStreamServlet mediaServlet = new MediaStreamServlet(new DefaultImageService());
        return new ServletRegistrationBean(mediaServlet, "/media", "/media/", "/media/*");
    }
}
