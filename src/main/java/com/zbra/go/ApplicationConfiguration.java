package com.zbra.go;

import com.zbra.go.controller.MediaStreamServlet;
import com.zbra.go.service.ImageService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class ApplicationConfiguration {

    @Bean
    public ServletRegistrationBean servletRegistrationBean(ImageService imageService) {
        final MediaStreamServlet mediaServlet = new MediaStreamServlet(imageService);
        return new ServletRegistrationBean(mediaServlet, "/media", "/media/", "/media/*");
    }

}
