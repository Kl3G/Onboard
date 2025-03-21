package com.example.Portfolio_Onboard.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addRedirectViewController("/", "/index");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/data/image/");
                //.addResourceLocations("file:///app/data/image/");

        registry.addResourceHandler("/boardImg/**")
                .addResourceLocations("file:///C:/data/boardImg/");
                //.addResourceLocations("file:///app/data/boardImg/");

        registry.addResourceHandler("/thumbnail/**")
                .addResourceLocations("file:///C:/data/image/thumbnail/");
                //.addResourceLocations("file:///app/data/boardImg/");
    }


}
