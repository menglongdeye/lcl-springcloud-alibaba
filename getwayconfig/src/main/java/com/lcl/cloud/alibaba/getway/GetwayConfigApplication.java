package com.lcl.cloud.alibaba.getway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GetwayConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetwayConfigApplication.class, args);
    }

    @Bean
    public RouteLocator someRouteLocator(RouteLocatorBuilder locatorBuilder){
        return locatorBuilder.routes().route(predicateSpec ->
                predicateSpec.path("/**").uri("https://www.cnblogs.com").id("sina_route")).build();
    }
}
